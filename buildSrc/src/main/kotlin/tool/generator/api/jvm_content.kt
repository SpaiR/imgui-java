package tool.generator.api

import spoon.reflect.code.CtJavaDoc
import spoon.reflect.code.CtJavaDocTag
import spoon.reflect.declaration.CtField
import spoon.reflect.declaration.CtMethod
import spoon.reflect.declaration.CtParameter
import spoon.reflect.declaration.ModifierKind
import kotlin.math.absoluteValue

private fun createStaticStructFieldName(method: CtMethod<*>): String {
    return "_${method.simpleName.uppercase()}_${method.parameters.hashCode().absoluteValue}"
}

private fun createStaticStructField(typeName: String, fieldName: String): String {
    return "private static final $typeName $fieldName = new $typeName(0);"
}

private fun joinInBodyParams(params: List<CtParameter<*>>, defaults: IntArray): String {
    fun param2str(p: CtParameter<*>): String {
        return if (p.type.isPtrClass()) {
            "${p.simpleName}.$PTR_JVM_FIELD"
        } else if (p.type.isClass) {
            when (p.type.simpleName) {
                "ImBoolean", "ImShort", "ImInt", "ImFloat", "ImLong", "ImDouble" -> {
                    "${p.simpleName} != null ? ${p.simpleName}.getData() : null"
                }

                "ImVec2" -> "${p.simpleName}.x, ${p.simpleName}.y"
                "ImVec4" -> "${p.simpleName}.x, ${p.simpleName}.y, ${p.simpleName}.z, ${p.simpleName}.w"
                "ImRect" -> "${p.simpleName}.min.x, ${p.simpleName}.min.y, ${p.simpleName}.max.x, ${p.simpleName}.max.y"

                "ImPlotPoint" -> "${p.simpleName}.x, ${p.simpleName}.y"
                "ImPlotRange" -> "${p.simpleName}.min, ${p.simpleName}.max"
                "ImPlotRect" -> "${p.simpleName}.x.min, ${p.simpleName}.y.min, ${p.simpleName}.x.max, ${p.simpleName}.y.max"

                "TextEditorCoordinates" -> "${p.simpleName}.mLine, ${p.simpleName}.mColumn"

                "String[]" -> "${p.simpleName}, ${p.simpleName}.length"

                "boolean[]", "byte[]", "short[]", "int[]", "float[]", "long[]", "double[]" -> p.simpleName

                else -> p.simpleName
            }
        } else {
            p.simpleName
        }
    }

    val visibleParams = mutableListOf<String>()
    for ((index, p) in params.withIndex()) {
        if (defaults.isNotEmpty() && !defaults.contains(index)) {
            continue
        }
        if (p.isType("Void")) {
            continue
        }
        visibleParams += param2str(p)
    }
    return visibleParams.joinToString()
}

private fun createBodyStaticStructReturn(
    method: CtMethod<*>,
    params: List<CtParameter<*>>,
    defaults: IntArray
): String {
    val fieldName = createStaticStructFieldName(method)
    return buildString {
        append("${fieldName}.$PTR_JVM_FIELD = ${method.getJniName()}(")
        append(joinInBodyParams(params, defaults))
        appendLine(");")
        append("return $fieldName")
    }
}

private fun createBodyStructReturn(
    method: CtMethod<*>,
    params: List<CtParameter<*>>,
    defaults: IntArray
): String {
    return buildString {
        append("return new ${method.type.simpleName}(${method.getJniName()}(")
        append(joinInBodyParams(params, defaults))
        append("))")
    }
}

private fun createBodyDstReturn(
    method: CtMethod<*>,
    params: List<CtParameter<*>>,
    defaults: IntArray
): String {
    val type = method.type.simpleName
    return buildString {
        appendLine("final $type dst = new ${type}();")
        append("${method.getJniName()}(dst")
        joinInBodyParams(params, defaults).let {
            if (it.isNotEmpty()) {
                append(", $it")
            }
        }
        appendLine(");")
        append("return dst")
    }
}

private fun createMethod(origM: CtMethod<*>, params: List<CtParameter<*>>, defaults: IntArray): CtMethod<*> {
    val f = origM.factory
    val newM = f.createMethod<Nothing>()

    if (origM.isPublic) {
        newM.addModifier<Nothing>(ModifierKind.PUBLIC)
    }
    if (origM.isStatic) {
        newM.addModifier<Nothing>(ModifierKind.STATIC)
    }

    if (origM.docComment.isNotBlank()) {
        newM.setDocComment<Nothing>(origM.docComment)
    }

    newM.setAnnotations<Nothing>(origM.annotations)
    newM.setType<Nothing>(origM.type)
    newM.setSimpleName<Nothing>(origM.getName())

    for ((index, p) in params.withIndex()) {
        if (defaults.isNotEmpty() && !defaults.contains(index)) {
            continue
        }
        if (p.isType("Void")) {
            continue
        }
        newM.addParameter<Nothing>(f.createParameter<Nothing>().apply {
            addModifier<Nothing>(ModifierKind.FINAL)
            setType<Nothing>(p.type)
            setSimpleName<Nothing>(p.simpleName)
        })
    }

    sanitizeDocComment(newM)
    sanitizeAnnotations(newM)

    newM.setBody<Nothing>(f.createCodeSnippet(
        buildString {
            if (origM.isStaticStructReturnValue()) {
                append(createBodyStaticStructReturn(origM, params, defaults))
            } else if (DST_RETURN_TYPE_SET.contains(origM.type.simpleName)) {
                append(createBodyDstReturn(origM, params, defaults))
            } else if (origM.type.isClass && !origM.type.isPrimitive && !origM.isType("void") && !origM.isType("String") && !origM.isPrimitivePtrType()) {
                append(createBodyStructReturn(origM, params, defaults))
            } else {
                if (!origM.isType("void")) {
                    append("return ")
                }
                append("${origM.getJniName()}(")
                append(joinInBodyParams(params, defaults))
                append(")")
            }
        }
    ))

    return newM
}

private fun methodVecUnwrappedContent(method: CtMethod<*>): String {
    val newMethod = method.clone()
    val newParams = mutableListOf<CtParameter<*>>()
    val vecParamNames = mutableSetOf<String>()

    for (p in newMethod.parameters) {
        if (p.isType("ImVec2") || p.isType("ImVec4")) {
            vecParamNames += p.simpleName

            val paramX = p.factory.createParameter<Nothing>()
            paramX.addModifier<Nothing>(ModifierKind.FINAL)
            paramX.setType<Nothing>(p.factory.createTypeParam("float"))
            paramX.setSimpleName<Nothing>("${p.simpleName}X")

            val paramY = paramX.clone()
            paramY.setSimpleName<Nothing>("${p.simpleName}Y")

            newParams += paramX
            newParams += paramY

            if (p.isType("ImVec4")) {
                val paramZ = paramX.clone()
                paramZ.setSimpleName<Nothing>("${p.simpleName}Z")
                val paramW = paramX.clone()
                paramW.setSimpleName<Nothing>("${p.simpleName}W")

                newParams += paramZ
                newParams += paramW
            }

            getJDoc(newMethod)?.let { jDoc ->
                val idx = jDoc.tags.indexOfFirst { it.param == p.simpleName }
                if (idx != -1) {
                    jDoc.removeTag<Nothing>(idx)
                }
            }
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<Nothing>(newParams)

    val mContentOrig = method.prettyprint()
    val mContent = newMethod.prettyprint().let {
        var str = it
        for (paramName in vecParamNames) {
            str = str.replace("${paramName}.x, ${paramName}.y", "${paramName}X, ${paramName}Y")
            str = str.replace("${paramName}.z, ${paramName}.w", "${paramName}Z, ${paramName}W")
        }
        str
    }

    if (mContent != mContentOrig) {
        return mContent
    }

    return ""
}

private fun methodRectUnwrappedContent(method: CtMethod<*>): String {
    val newMethod = method.clone()
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for (p in newMethod.parameters) {
        if (p.isType("ImRect")) {
            paramNames += p.simpleName

            val paramMinX = p.factory.createParameter<Nothing>()
            paramMinX.addModifier<Nothing>(ModifierKind.FINAL)
            paramMinX.setType<Nothing>(p.factory.createTypeParam("float"))
            paramMinX.setSimpleName<Nothing>("${p.simpleName}MinX")

            val paramMinY = paramMinX.clone()
            paramMinY.setSimpleName<Nothing>("${p.simpleName}MinY")

            newParams += paramMinX
            newParams += paramMinY

            val paramMaxX = paramMinX.clone()
            paramMaxX.setSimpleName<Nothing>("${p.simpleName}MaxX")
            val paramMaxY = paramMinX.clone()
            paramMaxY.setSimpleName<Nothing>("${p.simpleName}MaxY")

            newParams += paramMaxX
            newParams += paramMaxY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<Nothing>(newParams)

    val mContentOrig = method.prettyprint()
    val mContent = newMethod.prettyprint().let {
        var str = it
        for (paramName in paramNames) {
            str = str.replace(
                "${paramName}.min.x, ${paramName}.min.y, ${paramName}.max.x, ${paramName}.max.y",
                "${paramName}MinX, ${paramName}MinY, ${paramName}MaxX, ${paramName}MaxY"
            )
        }
        str
    }

    if (mContent != mContentOrig) {
        return mContent
    }

    return ""
}

private fun methodPlotPointUnwrappedContent(method: CtMethod<*>): String {
    val newMethod = method.clone()
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for (p in newMethod.parameters) {
        if (p.isType("ImPlotPoint")) {
            paramNames += p.simpleName

            val paramX = p.factory.createParameter<Nothing>()
            paramX.addModifier<Nothing>(ModifierKind.FINAL)
            paramX.setType<Nothing>(p.factory.createTypeParam("double"))
            paramX.setSimpleName<Nothing>("${p.simpleName}X")

            val paramY = paramX.clone()
            paramY.setSimpleName<Nothing>("${p.simpleName}Y")

            newParams += paramX
            newParams += paramY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<Nothing>(newParams)

    val mContentOrig = method.prettyprint()
    val mContent = newMethod.prettyprint().let {
        var str = it
        for (paramName in paramNames) {
            str = str.replace("${paramName}.x, ${paramName}.y", "${paramName}X, ${paramName}Y")
        }
        str
    }

    if (mContent != mContentOrig) {
        return mContent
    }

    return ""
}

private fun methodPlotRangeUnwrappedContent(method: CtMethod<*>): String {
    val newMethod = method.clone()
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for (p in newMethod.parameters) {
        if (p.isType("ImPlotRange")) {
            paramNames += p.simpleName

            val paramX = p.factory.createParameter<Nothing>()
            paramX.addModifier<Nothing>(ModifierKind.FINAL)
            paramX.setType<Nothing>(p.factory.createTypeParam("double"))
            paramX.setSimpleName<Nothing>("${p.simpleName}Min")

            val paramY = paramX.clone()
            paramY.setSimpleName<Nothing>("${p.simpleName}Max")

            newParams += paramX
            newParams += paramY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<Nothing>(newParams)

    val mContentOrig = method.prettyprint()
    val mContent = newMethod.prettyprint().let {
        var str = it
        for (paramName in paramNames) {
            str = str.replace("${paramName}.min, ${paramName}.max", "${paramName}Min, ${paramName}Max")
        }
        str
    }

    if (mContent != mContentOrig) {
        return mContent
    }

    return ""
}

private fun methodPlotLimitsUnwrappedContent(method: CtMethod<*>): String {
    val newMethod = method.clone()
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for (p in newMethod.parameters) {
        if (p.isType("ImPlotRect")) {
            paramNames += p.simpleName

            val paramMinX = p.factory.createParameter<Nothing>()
            paramMinX.addModifier<Nothing>(ModifierKind.FINAL)
            paramMinX.setType<Nothing>(p.factory.createTypeParam("double"))
            paramMinX.setSimpleName<Nothing>("${p.simpleName}MinX")

            val paramMinY = paramMinX.clone()
            paramMinY.setSimpleName<Nothing>("${p.simpleName}MinY")

            newParams += paramMinX
            newParams += paramMinY

            val paramMaxX = paramMinX.clone()
            paramMaxX.setSimpleName<Nothing>("${p.simpleName}MaxX")
            val paramMaxY = paramMinX.clone()
            paramMaxY.setSimpleName<Nothing>("${p.simpleName}MaxY")

            newParams += paramMaxX
            newParams += paramMaxY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<Nothing>(newParams)

    val mContentOrig = method.prettyprint()
    val mContent = newMethod.prettyprint().let {
        var str = it
        for (paramName in paramNames) {
            str = str.replace(
                "${paramName}.x.min, ${paramName}.y.min, ${paramName}.x.max, ${paramName}.y.max",
                "${paramName}MinX, ${paramName}MinY, ${paramName}MaxX, ${paramName}MaxY"
            )
        }
        str
    }

    if (mContent != mContentOrig) {
        return mContent
    }

    return ""
}

private fun methodCoordinatesUnwrappedContent(method: CtMethod<*>): String {
    val newMethod = method.clone()
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for (p in newMethod.parameters) {
        if (p.isType("TextEditorCoordinates")) {
            paramNames += p.simpleName

            val paramX = p.factory.createParameter<Nothing>()
            paramX.addModifier<Nothing>(ModifierKind.FINAL)
            paramX.setType<Nothing>(p.factory.createTypeParam("int"))
            paramX.setSimpleName<Nothing>("${p.simpleName}Line")

            val paramY = paramX.clone()
            paramY.setSimpleName<Nothing>("${p.simpleName}Column")

            newParams += paramX
            newParams += paramY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<Nothing>(newParams)

    val mContentOrig = method.prettyprint()
    val mContent = newMethod.prettyprint().let {
        var str = it
        for (paramName in paramNames) {
            str = str.replace("${paramName}.mLine, ${paramName}.mColumn", "${paramName}Line, ${paramName}Column")
        }
        str
    }

    if (mContent != mContentOrig) {
        return mContent
    }

    return ""
}

private fun createMethodDstReturn(
    mOrig: CtMethod<*>,
    params: List<CtParameter<*>>,
    defaults: IntArray
): CtMethod<*> {
    val mNew = mOrig.clone()
    mNew.setType<Nothing>(mNew.factory.createTypeParam("void"))
    mNew.addParameterAt<Nothing>(0, mOrig.factory.createParameter<Nothing>().apply {
        addModifier<Nothing>(ModifierKind.FINAL)
        setType<Nothing>(factory.createTypeParam(mOrig.type.simpleName))
        setSimpleName<Nothing>("dst")
    })
    mNew.setBody<Nothing>(mOrig.factory.createCodeSnippet(
        buildString {
            append("${mOrig.getJniName()}(dst")
            joinInBodyParams(params, defaults).let {
                if (it.isNotEmpty()) {
                    append(", $it")
                }
            }
            append(")")
        }
    ))
    return mNew
}

private fun createMethodVecValueReturn(
    vecVal: String,
    mOrig: CtMethod<*>,
    params: List<CtParameter<*>>,
    defaults: IntArray
): CtMethod<*> {
    val mNew = mOrig.clone()
    mNew.setSimpleName<Nothing>(mOrig.getName() + vecVal.capitalize())
    mNew.type.setSimpleName<Nothing>("float")
    mNew.setBody<Nothing>(mOrig.factory.createCodeSnippetStatement().apply {
        setValue<Nothing>(buildString {
            append("return ")
            append("${mNew.getJniName()}(")
            append(joinInBodyParams(params, defaults))
            append(")")
        })
    })
    return mNew
}

private fun transformMethodToContent(
    mOrig: CtMethod<*>,
    params: List<CtParameter<*>> = emptyList(),
    defaults: IntArray = intArrayOf()
): List<String> {
    val methods = mutableListOf<String>()
    val mNew = createMethod(mOrig, params, defaults)

    methods += mNew.prettyprint()

    if (params.find { it.isType("ImVec2") || it.isType("ImVec4") } != null) {
        methodVecUnwrappedContent(mNew).takeIf(String::isNotEmpty)?.run(methods::add)
    }

    if (params.find { it.isType("ImRect") } != null) {
        methodRectUnwrappedContent(mNew).takeIf(String::isNotEmpty)?.run(methods::add)
    }

    if (params.find { it.isType("ImPlotPoint") } != null) {
        methodPlotPointUnwrappedContent(mNew).takeIf(String::isNotEmpty)?.run(methods::add)
    }

    if (params.find { it.isType("ImPlotRange") } != null) {
        methodPlotRangeUnwrappedContent(mNew).takeIf(String::isNotEmpty)?.run(methods::add)
    }

    if (params.find { it.isType("ImPlotRect") } != null) {
        methodPlotLimitsUnwrappedContent(mNew).takeIf(String::isNotEmpty)?.run(methods::add)
    }

    if (params.find { it.isType("TextEditorCoordinates") } != null) {
        methodCoordinatesUnwrappedContent(mNew).takeIf(String::isNotEmpty)?.run(methods::add)
    }

    if (mOrig.isType("ImVec2") || mOrig.isType("ImVec4")) {
        methods += createMethodVecValueReturn("x", mNew, params, defaults).prettyprint()
        methods += createMethodVecValueReturn("y", mNew, params, defaults).prettyprint()
        if (mOrig.isType("ImVec4")) {
            methods += createMethodVecValueReturn("z", mNew, params, defaults).prettyprint()
            methods += createMethodVecValueReturn("w", mNew, params, defaults).prettyprint()
        }
    }

    if (DST_RETURN_TYPE_SET.contains(mOrig.type.simpleName)) {
        methods += createMethodDstReturn(mNew, params, defaults).prettyprint()
    }

    return methods
}

fun jvmMethodContent(method: CtMethod<*>): List<String> {
    val addContent = mutableListOf<String>()

    if (method.isStaticStructReturnValue()) {
        val staticStructFieldName = createStaticStructFieldName(method)
        addContent += createStaticStructField(method.type.simpleName, staticStructFieldName)
    }

    val methods = mutableListOf<String>()

    for (paramsSize in (findFirstOptParam(method)..method.parameters.size)) {
        val params = method.parameters.subList(0, paramsSize)
        methods += transformMethodToContent(method, params)
    }

    for (defaults in findDefaultsCombinations(method)) {
        methods += transformMethodToContent(method, method.parameters, defaults)
    }

    return addContent + methods
}

private fun CtMethod<*>.isStaticStructReturnValue(): Boolean {
    return !isType("void") && getAnnotation(A_NAME_RETURN_VALUE)?.getValueAsObject(A_VALUE_IS_STATIC) == true
}

private fun createFieldGetContent(field: CtField<*>): List<String> {
    val f = field.factory

    val getAccessor = f.createMethod<Nothing>()
    getAccessor.setParent<Nothing>(field.parent)
    getAccessor.setSimpleName<Nothing>("get${field.simpleName}")
    getAccessor.setType<Nothing>(field.type)
    getAccessor.addModifier<Nothing>(ModifierKind.PUBLIC)
    getAccessor.setDocComment<Nothing>(field.docComment)
    getAccessor.setAnnotations<Nothing>(field.annotations)

    val result = jvmMethodContent(getAccessor).toMutableList()

    if (field.hasAnnotation(A_NAME_TYPE_ARRAY)) {
        when (val arrayType = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_TYPE)) {
            "boolean", "short", "int", "float", "double", "long" -> {
                val newM = getAccessor.clone()
                newM.setType<Nothing>(f.createTypeParam(arrayType))
                newM.addParameter<Nothing>(f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam("int"))
                    setSimpleName<Nothing>("idx")
                })
                result += jvmMethodContent(newM)
            }
        }
    }

    return result
}

private fun createFieldSetContent(field: CtField<*>): List<String> {
    val f = field.factory

    val setAccessor = f.createMethod<Nothing>()
    setAccessor.setType<Nothing>(f.createTypeParam("void"))
    setAccessor.setParent<Nothing>(field.parent)
    setAccessor.setSimpleName<Nothing>("set${field.simpleName}")
    setAccessor.addModifier<Nothing>(ModifierKind.PUBLIC)
    setAccessor.setDocComment<Nothing>(field.docComment)
    setAccessor.setAnnotations<Nothing>(field.annotations)

    val valueParam = f.createParameter<Nothing>().apply {
        setType<Nothing>(field.type)
        setSimpleName<Nothing>("value")
    }

    val result = transformMethodToContent(setAccessor, listOf(valueParam)).toMutableList()

    if (field.hasAnnotation(A_NAME_TYPE_ARRAY)) {
        when (val arrayType = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_TYPE)) {
            "boolean", "short", "int", "float", "double", "long" -> {
                val newM = setAccessor.clone()
                newM.parameters.clear()
                newM.addParameter<Nothing>(f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam("int"))
                    setSimpleName<Nothing>("idx")
                })
                newM.addParameter<Nothing>(f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam(arrayType))
                    setSimpleName<Nothing>("value")
                })
                result += transformMethodToContent(newM, newM.parameters)
            }
        }
    }

    return result
}

private fun createFieldFlagUtils(field: CtField<*>): List<String> {
    val mAdd = field.factory.createMethod<Nothing>()
    mAdd.setSimpleName<Nothing>("add${field.simpleName}")
    mAdd.setType<Nothing>(field.factory.createTypeParam("void"))
    mAdd.addModifier<Nothing>(ModifierKind.PUBLIC)
    mAdd.setDocComment<Nothing>(field.docComment)
    mAdd.addParameter<Nothing>(field.factory.createParameter<Nothing>().apply {
        addModifier<Nothing>(ModifierKind.FINAL)
        setType<Nothing>(field.factory.createTypeParam("int"))
        setSimpleName<Nothing>("flags")
    })
    mAdd.setBody<Nothing>(field.factory.createCodeSnippet("set${field.simpleName}(get${field.simpleName}() | flags)"))

    val mRemove = mAdd.clone()
    mRemove.setSimpleName<Nothing>("remove${field.simpleName}")
    mRemove.setBody<Nothing>(field.factory.createCodeSnippet("set${field.simpleName}(get${field.simpleName}() & ~(flags))"))

    val mHas = mAdd.clone()
    mHas.setSimpleName<Nothing>("has${field.simpleName}")
    mHas.setType<Nothing>(field.factory.createTypeParam("boolean"))
    mHas.setBody<Nothing>(field.factory.createCodeSnippet("return (get${field.simpleName}() & flags) != 0"))

    val utilMethods = mutableListOf<String>()
    val bindingFieldAnnotation = field.getAnnotation(A_NAME_BINDING_FIELD)

    if (bindingFieldAnnotation?.containsValue(A_VALUE_ACCESSORS, A_TYPE_VALUE_ACCESSOR_SETTER) == true) {
        utilMethods += mAdd.prettyprint()
        utilMethods += mRemove.prettyprint()
    }

    if (bindingFieldAnnotation?.containsValue(A_VALUE_ACCESSORS, A_TYPE_VALUE_ACCESSOR_GETTER) == true) {
        utilMethods += mHas.prettyprint()
    }

    return utilMethods
}

fun jvmFieldContent(field: CtField<*>): List<String> {
    val fields = mutableListOf<String>()
    val a = field.getAnnotation(A_NAME_BINDING_FIELD)
    if (a?.containsValue(A_VALUE_ACCESSORS, A_TYPE_VALUE_ACCESSOR_GETTER) == true) {
        fields += createFieldGetContent(field)
    }
    if (a?.containsValue(A_VALUE_ACCESSORS, A_TYPE_VALUE_ACCESSOR_SETTER) == true) {
        fields += createFieldSetContent(field)
    }
    if (field.getAnnotation(A_NAME_BINDING_FIELD)?.getValueAsObject(A_VALUE_IS_FLAG) == true) {
        fields += createFieldFlagUtils(field)
    }
    return fields
}

private fun getJDoc(method: CtMethod<*>): CtJavaDoc? {
    return method.comments.filterIsInstance<CtJavaDoc>().firstOrNull()
}

private fun sanitizeDocComment(method: CtMethod<*>) {
    val jDoc = getJDoc(method) ?: return
    val paramNames = method.parameters.map { it.simpleName }
    jDoc.setTags<Nothing>(jDoc.tags.filter { it.type != CtJavaDocTag.TagType.PARAM || paramNames.contains(it.param) })
}

private fun sanitizeAnnotations(method: CtMethod<*>) {
    method.setAnnotations<Nothing>(method.annotations.filter { !CLEANUP_ANNOTATIONS_LIST.contains(it.name) })
}
