package tool.generator.api

import spoon.reflect.code.CtBodyHolder
import spoon.reflect.code.CtJavaDoc
import spoon.reflect.code.CtJavaDocTag
import spoon.reflect.reference.CtReference
import spoon.reflect.declaration.CtCodeSnippet
import spoon.reflect.declaration.CtElement
import spoon.reflect.declaration.CtExecutable
import spoon.reflect.declaration.CtField
import spoon.reflect.declaration.CtMethod
import spoon.reflect.declaration.CtModifiable
import spoon.reflect.declaration.CtNamedElement
import spoon.reflect.declaration.CtParameter
import spoon.reflect.declaration.CtTypedElement
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
        } else {
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
    val newM = f.createMethod<Any>()

    if (origM.isPublic) {
        newM.addModifier<CtModifiable>(ModifierKind.PUBLIC)
    }
    if (origM.isStatic) {
        newM.addModifier<CtModifiable>(ModifierKind.STATIC)
    }

    if (origM.docComment.isNotBlank()) {
        newM.setDocComment<CtElement>(origM.docComment)
    }

    newM.setAnnotations<CtElement>(origM.annotations)
    newM.setType<CtTypedElement<Any>>(origM.type)
    newM.setSimpleName<CtNamedElement>(origM.getName())

    for ((index, p) in params.withIndex()) {
        if (defaults.isNotEmpty() && !defaults.contains(index)) {
            continue
        }
        if (p.isType("Void")) {
            continue
        }
        newM.addParameter<CtMethod<Any>>(f.createParameter<Any>().apply {
            addModifier<CtModifiable>(ModifierKind.FINAL)
            setType<CtTypedElement<Any>>(p.type)
            setSimpleName<CtNamedElement>(p.simpleName)
        })
    }

    sanitizeDocComment(newM)
    sanitizeAnnotations(newM)

    newM.setBody<CtBodyHolder>(f.createCodeSnippet(
        buildString {
            if (origM.isStaticStructReturnValue()) {
                append(createBodyStaticStructReturn(origM, params, defaults))
            } else if (DST_RETURN_TYPE_SET.contains(origM.type.simpleName)) {
                append(createBodyDstReturn(origM, params, defaults))
            } else if (origM.type.isPtrClass()) {
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

private fun methodVecUnwrappedContent(method: CtMethod<*>, fromIndex: Int): String {
    @Suppress("UNCHECKED_CAST") val newMethod = method.clone() as CtMethod<Any>
    val newParams = mutableListOf<CtParameter<*>>()
    val vecParamNames = mutableSetOf<String>()

    for ((idx, p) in newMethod.parameters.withIndex()) {
        if ((p.isType("ImVec2") || p.isType("ImVec4")) && idx >= fromIndex) {
            vecParamNames += p.simpleName

            val paramX = p.factory.createParameter<Any>()
            paramX.addModifier<CtModifiable>(ModifierKind.FINAL)
            paramX.setType<CtTypedElement<Any>>(p.factory.createTypeParam("float"))
            paramX.setSimpleName<CtNamedElement>("${p.simpleName}X")

            val paramY = paramX.clone()
            paramY.setSimpleName<CtNamedElement>("${p.simpleName}Y")

            newParams += paramX
            newParams += paramY

            if (p.isType("ImVec4")) {
                val paramZ = paramX.clone()
                paramZ.setSimpleName<CtNamedElement>("${p.simpleName}Z")
                val paramW = paramX.clone()
                paramW.setSimpleName<CtNamedElement>("${p.simpleName}W")

                newParams += paramZ
                newParams += paramW
            }

            getJDoc(newMethod)?.let { jDoc ->
                val tagIdx = jDoc.tags.indexOfFirst { it.param == p.simpleName }
                if (tagIdx != -1) {
                    jDoc.removeTag<CtJavaDoc>(tagIdx)
                }
            }
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<CtMethod<Any>>(newParams)

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

private fun methodRectUnwrappedContent(method: CtMethod<*>, fromIndex: Int): String {
    @Suppress("UNCHECKED_CAST") val newMethod = method.clone() as CtMethod<Any>
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for ((idx, p) in newMethod.parameters.withIndex()) {
        if (p.isType("ImRect") && idx >= fromIndex) {
            paramNames += p.simpleName

            val paramMinX = p.factory.createParameter<Any>()
            paramMinX.addModifier<CtModifiable>(ModifierKind.FINAL)
            paramMinX.setType<CtTypedElement<Any>>(p.factory.createTypeParam("float"))
            paramMinX.setSimpleName<CtNamedElement>("${p.simpleName}MinX")

            val paramMinY = paramMinX.clone()
            paramMinY.setSimpleName<CtNamedElement>("${p.simpleName}MinY")

            newParams += paramMinX
            newParams += paramMinY

            val paramMaxX = paramMinX.clone()
            paramMaxX.setSimpleName<CtNamedElement>("${p.simpleName}MaxX")
            val paramMaxY = paramMinX.clone()
            paramMaxY.setSimpleName<CtNamedElement>("${p.simpleName}MaxY")

            newParams += paramMaxX
            newParams += paramMaxY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<CtMethod<Any>>(newParams)

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

private fun methodPlotPointUnwrappedContent(method: CtMethod<*>, fromIndex: Int): String {
    @Suppress("UNCHECKED_CAST") val newMethod = method.clone() as CtMethod<Any>
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for ((idx, p) in newMethod.parameters.withIndex()) {
        if (p.isType("ImPlotPoint") && idx >= fromIndex) {
            paramNames += p.simpleName

            val paramX = p.factory.createParameter<Any>()
            paramX.addModifier<CtModifiable>(ModifierKind.FINAL)
            paramX.setType<CtTypedElement<Any>>(p.factory.createTypeParam("double"))
            paramX.setSimpleName<CtNamedElement>("${p.simpleName}X")

            val paramY = paramX.clone()
            paramY.setSimpleName<CtNamedElement>("${p.simpleName}Y")

            newParams += paramX
            newParams += paramY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<CtMethod<Any>>(newParams)

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

private fun methodPlotRangeUnwrappedContent(method: CtMethod<*>, fromIndex: Int): String {
    @Suppress("UNCHECKED_CAST") val newMethod = method.clone() as CtMethod<Any>
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for ((idx, p) in newMethod.parameters.withIndex()) {
        if (p.isType("ImPlotRange") && idx >= fromIndex) {
            paramNames += p.simpleName

            val paramX = p.factory.createParameter<Any>()
            paramX.addModifier<CtModifiable>(ModifierKind.FINAL)
            paramX.setType<CtTypedElement<Any>>(p.factory.createTypeParam("double"))
            paramX.setSimpleName<CtNamedElement>("${p.simpleName}Min")

            val paramY = paramX.clone()
            paramY.setSimpleName<CtNamedElement>("${p.simpleName}Max")

            newParams += paramX
            newParams += paramY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<CtMethod<Any>>(newParams)

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

private fun methodPlotLimitsUnwrappedContent(method: CtMethod<*>, fromIndex: Int): String {
    @Suppress("UNCHECKED_CAST") val newMethod = method.clone() as CtMethod<Any>
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for ((idx, p) in newMethod.parameters.withIndex()) {
        if (p.isType("ImPlotRect") && idx >= fromIndex) {
            paramNames += p.simpleName

            val paramMinX = p.factory.createParameter<Any>()
            paramMinX.addModifier<CtModifiable>(ModifierKind.FINAL)
            paramMinX.setType<CtTypedElement<Any>>(p.factory.createTypeParam("double"))
            paramMinX.setSimpleName<CtNamedElement>("${p.simpleName}MinX")

            val paramMinY = paramMinX.clone()
            paramMinY.setSimpleName<CtNamedElement>("${p.simpleName}MinY")

            newParams += paramMinX
            newParams += paramMinY

            val paramMaxX = paramMinX.clone()
            paramMaxX.setSimpleName<CtNamedElement>("${p.simpleName}MaxX")
            val paramMaxY = paramMinX.clone()
            paramMaxY.setSimpleName<CtNamedElement>("${p.simpleName}MaxY")

            newParams += paramMaxX
            newParams += paramMaxY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<CtMethod<Any>>(newParams)

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

private fun methodCoordinatesUnwrappedContent(method: CtMethod<*>, fromIndex: Int): String {
    @Suppress("UNCHECKED_CAST") val newMethod = method.clone() as CtMethod<Any>
    val newParams = mutableListOf<CtParameter<*>>()
    val paramNames = mutableSetOf<String>()

    for ((idx, p) in newMethod.parameters.withIndex()) {
        if (p.isType("TextEditorCoordinates") && idx >= fromIndex) {
            paramNames += p.simpleName

            val paramX = p.factory.createParameter<Any>()
            paramX.addModifier<CtModifiable>(ModifierKind.FINAL)
            paramX.setType<CtTypedElement<Any>>(p.factory.createTypeParam("int"))
            paramX.setSimpleName<CtNamedElement>("${p.simpleName}Line")

            val paramY = paramX.clone()
            paramY.setSimpleName<CtNamedElement>("${p.simpleName}Column")

            newParams += paramX
            newParams += paramY
        } else {
            newParams += p
        }
    }

    newMethod.setParameters<CtMethod<Any>>(newParams)

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
    @Suppress("UNCHECKED_CAST") val mNew = mOrig.clone() as CtMethod<Any>
    mNew.setType<CtTypedElement<Any>>(mNew.factory.createTypeParam("void"))
    mNew.addParameterAt<CtMethod<Any>>(0, mOrig.factory.createParameter<Any>().apply {
        addModifier<CtModifiable>(ModifierKind.FINAL)
        setType<CtTypedElement<Any>>(factory.createTypeParam(mOrig.type.simpleName))
        setSimpleName<CtNamedElement>("dst")
    })
    mNew.setBody<CtBodyHolder>(mOrig.factory.createCodeSnippet(
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
    @Suppress("UNCHECKED_CAST") val mNew = mOrig.clone() as CtMethod<Any>
    mNew.setSimpleName<CtNamedElement>(mOrig.getName() + vecVal.capitalize())
    mNew.type.setSimpleName<CtReference>("float")
    mNew.setBody<CtBodyHolder>(mOrig.factory.createCodeSnippetStatement().apply {
        setValue<CtCodeSnippet>(buildString {
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
    fun methodUnwrapped(method: CtMethod<*>, fromIndex: Int = 0): Set<String> {
        val result = mutableSetOf<String>()
        if (params.find { it.isType("ImVec2") || it.isType("ImVec4") } != null) {
            methodVecUnwrappedContent(method, fromIndex).takeIf(String::isNotEmpty)?.run(result::add)
        }
        if (params.find { it.isType("ImRect") } != null) {
            methodRectUnwrappedContent(method, fromIndex).takeIf(String::isNotEmpty)?.run(result::add)
        }
        if (params.find { it.isType("ImPlotPoint") } != null) {
            methodPlotPointUnwrappedContent(method, fromIndex).takeIf(String::isNotEmpty)?.run(result::add)
        }
        if (params.find { it.isType("ImPlotRange") } != null) {
            methodPlotRangeUnwrappedContent(method, fromIndex).takeIf(String::isNotEmpty)?.run(result::add)
        }
        if (params.find { it.isType("ImPlotRect") } != null) {
            methodPlotLimitsUnwrappedContent(method, fromIndex).takeIf(String::isNotEmpty)?.run(result::add)
        }
        if (params.find { it.isType("TextEditorCoordinates") } != null) {
            methodCoordinatesUnwrappedContent(method, fromIndex).takeIf(String::isNotEmpty)?.run(result::add)
        }
        return result
    }

    val methods = mutableListOf<String>()
    val mNew = createMethod(mOrig, params, defaults)

    methods += mNew.prettyprint()
    methods += methodUnwrapped(mNew)

    if (mOrig.isType("ImVec2") || mOrig.isType("ImVec4")) {
        methods += createMethodVecValueReturn("x", mNew, params, defaults).prettyprint()
        methods += createMethodVecValueReturn("y", mNew, params, defaults).prettyprint()
        if (mOrig.isType("ImVec4")) {
            methods += createMethodVecValueReturn("z", mNew, params, defaults).prettyprint()
            methods += createMethodVecValueReturn("w", mNew, params, defaults).prettyprint()
        }
    }

    if (DST_RETURN_TYPE_SET.contains(mOrig.type.simpleName)) {
        val dstMethod = createMethodDstReturn(mNew, params, defaults)
        methods += dstMethod.prettyprint()
        methods += methodUnwrapped(dstMethod, 1)
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

    val getAccessor = f.createMethod<Any>()
    getAccessor.setParent<CtElement>(field.parent)
    getAccessor.setSimpleName<CtNamedElement>("get${field.simpleName}")
    getAccessor.setType<CtTypedElement<Any>>(field.type)
    getAccessor.addModifier<CtModifiable>(ModifierKind.PUBLIC)
    getAccessor.setDocComment<CtElement>(field.docComment)
    getAccessor.setAnnotations<CtElement>(field.annotations)

    val result = jvmMethodContent(getAccessor).toMutableList()

    if (field.hasAnnotation(A_NAME_TYPE_ARRAY)) {
        when (val arrayType = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_TYPE)) {
            "boolean", "short", "int", "float", "double", "long" -> {
                val newM = getAccessor.clone()
                newM.setType<CtTypedElement<Any>>(f.createTypeParam(arrayType))
                newM.addParameter<CtMethod<Any>>(f.createParameter<Any>().apply {
                    setType<CtTypedElement<Any>>(f.createTypeParam("int"))
                    setSimpleName<CtNamedElement>("idx")
                })
                result += jvmMethodContent(newM)
            }
        }
    }

    return result
}

private fun createFieldSetContent(field: CtField<*>): List<String> {
    val f = field.factory

    val setAccessor = f.createMethod<Any>()
    setAccessor.setType<CtTypedElement<Any>>(f.createTypeParam("void"))
    setAccessor.setParent<CtElement>(field.parent)
    setAccessor.setSimpleName<CtNamedElement>("set${field.simpleName}")
    setAccessor.addModifier<CtModifiable>(ModifierKind.PUBLIC)
    setAccessor.setDocComment<CtElement>(field.docComment)
    setAccessor.setAnnotations<CtElement>(field.annotations)

    val valueParam = f.createParameter<Any>().apply {
        setType<CtTypedElement<Any>>(field.type)
        setSimpleName<CtNamedElement>("value")
    }

    val result = transformMethodToContent(setAccessor, listOf(valueParam)).toMutableList()

    if (field.hasAnnotation(A_NAME_TYPE_ARRAY)) {
        when (val arrayType = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_TYPE)) {
            "boolean", "short", "int", "float", "double", "long" -> {
                val newM = setAccessor.clone()
                newM.parameters.clear()
                newM.addParameter<CtMethod<Any>>(f.createParameter<Any>().apply {
                    setType<CtTypedElement<Any>>(f.createTypeParam("int"))
                    setSimpleName<CtNamedElement>("idx")
                })
                newM.addParameter<CtMethod<Any>>(f.createParameter<Any>().apply {
                    setType<CtTypedElement<Any>>(f.createTypeParam(arrayType))
                    setSimpleName<CtNamedElement>("value")
                })
                result += transformMethodToContent(newM, newM.parameters)
            }
        }
    }

    return result
}

private fun createFieldFlagUtils(field: CtField<*>): List<String> {
    val mAdd = field.factory.createMethod<Any>()
    mAdd.setSimpleName<CtNamedElement>("add${field.simpleName}")
    mAdd.setType<CtTypedElement<Any>>(field.factory.createTypeParam("void"))
    mAdd.addModifier<CtModifiable>(ModifierKind.PUBLIC)
    mAdd.setDocComment<CtElement>(field.docComment)
    mAdd.addParameter<CtMethod<Any>>(field.factory.createParameter<Any>().apply {
        addModifier<CtModifiable>(ModifierKind.FINAL)
        setType<CtTypedElement<Any>>(field.factory.createTypeParam("int"))
        setSimpleName<CtNamedElement>("flags")
    })
    mAdd.setBody<CtBodyHolder>(field.factory.createCodeSnippet("set${field.simpleName}(get${field.simpleName}() | flags)"))

    val mRemove = mAdd.clone()
    mRemove.setSimpleName<CtNamedElement>("remove${field.simpleName}")
    mRemove.setBody<CtBodyHolder>(field.factory.createCodeSnippet("set${field.simpleName}(get${field.simpleName}() & ~(flags))"))

    val mHas = mAdd.clone()
    mHas.setSimpleName<CtNamedElement>("has${field.simpleName}")
    mHas.setType<CtTypedElement<Any>>(field.factory.createTypeParam("boolean"))
    mHas.setBody<CtBodyHolder>(field.factory.createCodeSnippet("return (get${field.simpleName}() & flags) != 0"))

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
    jDoc.setTags<CtJavaDoc>(jDoc.tags.filter { it.type != CtJavaDocTag.TagType.PARAM || paramNames.contains(it.param) })
}

private fun sanitizeAnnotations(method: CtMethod<*>) {
    method.setAnnotations<CtElement>(method.annotations.filter { !CLEANUP_ANNOTATIONS_LIST.contains(it.name) })
}
