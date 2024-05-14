package tool.generator.api

import spoon.reflect.code.CtStatement
import spoon.reflect.declaration.CtField
import spoon.reflect.declaration.CtMethod
import spoon.reflect.declaration.CtParameter
import spoon.reflect.declaration.ModifierKind
import spoon.reflect.factory.Factory
import spoon.reflect.reference.CtTypeReference

private fun convertParams2jni(f: Factory, params: List<CtParameter<*>>, defaults: IntArray): List<CtParameter<*>> {
    val result = mutableListOf<CtParameter<*>>()
    for ((index, p) in params.withIndex()) {
        if (defaults.isNotEmpty() && !defaults.contains(index)) {
            continue
        }
        if (p.isType("Void")) {
            continue
        }
        if (p.isType("ImVec2") || p.isType("ImVec4")) { // vec param always accepted as primitive values
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("float"))
                setSimpleName<Nothing>("${p.simpleName}X")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("float"))
                setSimpleName<Nothing>("${p.simpleName}Y")
            }
            if (p.isType("ImVec4")) {
                result += f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam("float"))
                    setSimpleName<Nothing>("${p.simpleName}Z")
                }
                result += f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam("float"))
                    setSimpleName<Nothing>("${p.simpleName}W")
                }
            }
        } else if (p.isType("ImRect")) {
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("float"))
                setSimpleName<Nothing>("${p.simpleName}MinX")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("float"))
                setSimpleName<Nothing>("${p.simpleName}MinY")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("float"))
                setSimpleName<Nothing>("${p.simpleName}MaxX")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("float"))
                setSimpleName<Nothing>("${p.simpleName}MaxY")
            }
        } else if (p.isType("ImPlotPoint")) {
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}X")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}Y")
            }
        } else if (p.isType("ImPlotRange")) {
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}Min")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}Max")
            }
        } else if (p.isType("ImPlotLimits")) {
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}MinX")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}MinY")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}MaxX")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("double"))
                setSimpleName<Nothing>("${p.simpleName}MaxY")
            }
        } else if (p.isType("TextEditorCoordinates")) {
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("int"))
                setSimpleName<Nothing>("${p.simpleName}Line")
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("int"))
                setSimpleName<Nothing>("${p.simpleName}Column")
            }
        } else if (p.isType("String[]")) {
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("String[]"))
                setSimpleName<Nothing>(p.simpleName)
            }
            result += f.createParameter<Nothing>().apply {
                setType<Nothing>(f.createTypeParam("int"))
                setSimpleName<Nothing>("${p.simpleName}$PARAM_ARR_LEN_POSTFIX")
            }
        } else {
            result += f.createParameter<Nothing>().apply {
                val type = if (p.type.isPtrClass()) {
                    f.createTypeParam("long")
                } else if (p.type.isClass) {
                    when (p.type.simpleName) {
                        "ImBoolean" -> f.createTypeParam("boolean[]")
                        "ImInt" -> f.createTypeParam("int[]")
                        "ImFloat" -> f.createTypeParam("float[]")
                        "ImLong" -> f.createTypeParam("long[]")
                        "ImDouble" -> f.createTypeParam("double[]")
                        else -> p.type
                    }
                } else {
                    p.type
                }

                setType<Nothing>(type)
                setSimpleName<Nothing>(p.simpleName)
            }
        }
    }
    return result
}

private fun joinInBodyParams(params: List<CtParameter<*>>, defaults: IntArray): String {
    fun param2str(p: CtParameter<*>): String {
        return if (p.type.isPtrClass()) {
            "reinterpret_cast<${p.type.simpleName}*>(${p.simpleName})"
        } else if (p.isPrimitivePtrType()) {
            "&${p.simpleName}[0]"
        } else if (p.type.isClass) {
            when (p.type.simpleName) {
                "ImBoolean", "ImInt", "ImFloat", "ImLong", "ImDouble" -> {
                    "(${p.simpleName} != NULL ? &${p.simpleName}[0] : NULL)"
                }

                "ImRect" -> {
                    "ImRect(${p.simpleName}MinX, ${p.simpleName}MinY, ${p.simpleName}MaxX, ${p.simpleName}MaxY)"
                }

                "ImPlotPoint" -> {
                    "ImPlotPoint(${p.simpleName}X, ${p.simpleName}Y)"
                }

                "ImPlotRange" -> {
                    "ImPlotRange(${p.simpleName}Min, ${p.simpleName}Max)"
                }

                "ImPlotLimits" -> {
                    "ImPlotLimits(${p.simpleName}MinX, ${p.simpleName}MinY, ${p.simpleName}MaxX, ${p.simpleName}MaxY)"
                }

                "TextEditorCoordinates" -> {
                    "TextEditor::Coordinates(${p.simpleName}Line, ${p.simpleName}Column)"
                }

                else -> p.simpleName
            }
        } else {
            p.simpleName
        }
    }

    val visibleParams = mutableListOf<String>()
    for ((index, p) in params.withIndex()) {
        if (defaults.isNotEmpty() && !defaults.contains(index)) {
            visibleParams += p.getAnnotation(A_NAME_OPT_ARG)!!.getValueAsString(A_VALUE_CALL_VALUE)
            continue
        }
        if (p.isType("Void")) {
            visibleParams += p.getAnnotation(A_NAME_ARG_VALUE)?.getValueAsString(A_VALUE_CALL_VALUE) ?: p.simpleName
            continue
        }
        var value = param2str(p)
        p.getAnnotation(A_NAME_ARG_VALUE)?.let { a ->
            a.getValueAsString(A_VALUE_CALL_VALUE).takeIf(String::isNotEmpty)?.let { v ->
                value = v
            }
            a.getValueAsString(A_VALUE_CALL_PREFIX).takeIf(String::isNotEmpty)?.let { v ->
                value = v + value
            }
            a.getValueAsString(A_VALUE_CALL_SUFFIX).takeIf(String::isNotEmpty)?.let { v ->
                value += v
            }
            a.getValueAsString(A_VALUE_STATIC_CAST).takeIf(String::isNotEmpty)?.let { v ->
                value = "static_cast<$v>($value)"
            }
            a.getValueAsString(A_VALUE_REINTERPRET_CAST).takeIf(String::isNotEmpty)?.let { v ->
                value = "reinterpret_cast<$v>($value)"
            }
        }
        visibleParams += value
    }
    return visibleParams.joinToString()
}

private fun createMethod(mOrig: CtMethod<*>, params: List<CtParameter<*>>, defaults: IntArray): CtMethod<*> {
    val f = mOrig.factory
    val newM = f.createMethod<Nothing>()

    newM.setParent<Nothing>(mOrig.parent)

    newM.addModifier<Nothing>(ModifierKind.PRIVATE)
    newM.addModifier<Nothing>(ModifierKind.NATIVE)
    if (mOrig.isStatic) {
        newM.addModifier<Nothing>(ModifierKind.STATIC)
    }

    if (DST_RETURN_TYPE_SET.contains(mOrig.type.simpleName)) {
        newM.setType<Nothing>(f.createTypeParam("void"))
    } else if (mOrig.type.isPtrClass()) { // classes returned as a pointer
        newM.setType<Nothing>(f.createTypeParam("long"))
    } else {
        newM.setType<Nothing>(mOrig.type)
    }

    newM.setSimpleName<Nothing>(mOrig.getJniName())

    convertParams2jni(f, params, defaults).forEach {
        newM.addParameter<Nothing>(it)
    }

    if (DST_RETURN_TYPE_SET.contains(mOrig.type.simpleName)) {
        newM.addParameterAt<Nothing>(0, f.createParameter<Nothing>().apply {
            setType<Nothing>(mOrig.type)
            setSimpleName<Nothing>("dst")
        })
    }

    newM.setBody<Nothing>(f.createCodeSnippet(
        buildString {
            val jniCpyReturn = DST_RETURN_TYPE_SET.contains(mOrig.type.simpleName)
            val isStrReturn = mOrig.isType("String")

            if (jniCpyReturn) {
                append("Jni::${mOrig.type.simpleName}Cpy(env, ")
            } else {
                if (!mOrig.isType("void")) {
                    append("return ")
                    if (isStrReturn) {
                        append("env->NewStringUTF(")
                    } else if (!mOrig.type.isPrimitive) {
                        append("($PTR_JNI_CAST)")
                    }
                    mOrig.getAnnotation(A_NAME_RETURN_VALUE)?.let { a ->
                        append(a.getValueAsString(A_VALUE_CALL_PREFIX))
                    }
                }
            }

            val callName = mOrig.getAnnotation(A_NAME_BINDING_METHOD)?.let { a ->
                a.getValueAsString(A_VALUE_CALL_NAME)?.takeIf(String::isNotEmpty)
            } ?: mOrig.simpleName

            val callPtr = mOrig.parent.getAnnotation(A_NAME_BINDING_SOURCE)!!.let { a ->
                a.getValueAsString(A_VALUE_CALL_PTR)?.takeIf(String::isNotEmpty) ?: if (mOrig.isStatic) {
                    mOrig.declaringType.simpleName
                } else {
                    PTR_JNI_THIS
                }
            }

            val callOperator = mOrig.parent.getAnnotation(A_NAME_BINDING_SOURCE)!!.let { a ->
                a.getValueAsString(A_VALUE_CALL_OPERATOR)?.takeIf(String::isNotEmpty) ?: if (mOrig.isStatic) {
                    "::"
                } else {
                    "->"
                }
            }

            append("$callPtr$callOperator${callName}(")
            append(joinInBodyParams(params, defaults))
            append(')')

            mOrig.getAnnotation(A_NAME_RETURN_VALUE)?.let { a ->
                append(a.getValueAsString(A_VALUE_CALL_SUFFIX))
            }

            if (jniCpyReturn) {
                append(", dst)")
            } else if (isStrReturn) {
                append(')')
            }
        }
    ))

    return newM
}

private fun createMethodVecValueReturn(
    vecVal: String,
    mOrig: CtMethod<*>,
    nameOrig: String,
    params: List<CtParameter<*>>,
    defaults: IntArray
): CtMethod<*> {
    val callPtr = mOrig.parent.getAnnotation(A_NAME_BINDING_SOURCE)!!.let { a ->
        a.getValueAsString(A_VALUE_CALL_PTR)?.takeIf(String::isNotEmpty) ?: if (mOrig.isStatic) {
            mOrig.declaringType.simpleName
        } else {
            PTR_JNI_THIS
        }
    }

    val callOperator = mOrig.parent.getAnnotation(A_NAME_BINDING_SOURCE)!!.let { a ->
        a.getValueAsString(A_VALUE_CALL_OPERATOR)?.takeIf(String::isNotEmpty) ?: if (mOrig.isStatic) {
            "::"
        } else {
            "->"
        }
    }

    val mNew = mOrig.clone()
    mNew.setSimpleName<Nothing>(mOrig.simpleName + vecVal.capitalize())
    mNew.removeParameter(mNew.parameters[0]) // dst param which was added during orig method creation
    mNew.type.setSimpleName<Nothing>("float")
    mNew.setBody<Nothing>(mOrig.factory.createCodeSnippet(
        buildString {
            append("return ")
            append("$callPtr$callOperator$nameOrig(")
            append(joinInBodyParams(params, defaults))
            append(").$vecVal")
        }
    ))
    return mNew
}

private fun convertManualJni(mOrig: CtMethod<*>, method: CtMethod<*>): CtMethod<*>? {
    val mNew = method.clone()

    val getLines = mutableListOf<String>()
    val releaseLines = mutableListOf<String>()

    mNew.parameters.forEach { p ->
        var isConverted = false

        if (p.isPrimitivePtrType()) {
            val typeCast = PRIMITIVE_PTR_TYPECAST_MAP[p.type.simpleName]
            getLines += "auto ${p.simpleName} = obj_${p.simpleName} == NULL ? NULL : (${typeCast})env->GetPrimitiveArrayCritical(obj_${p.simpleName}, JNI_FALSE)"
            releaseLines += "if (${p.simpleName} != NULL) env->ReleasePrimitiveArrayCritical(obj_${p.simpleName}, ${p.simpleName}, JNI_FALSE)"
            isConverted = true
        }

        if (p.isType("String")) {
            getLines += "auto ${p.simpleName} = obj_${p.simpleName} == NULL ? NULL : (char*)env->GetStringUTFChars(obj_${p.simpleName}, JNI_FALSE)"
            releaseLines += "if (${p.simpleName} != NULL) env->ReleaseStringUTFChars(obj_${p.simpleName}, ${p.simpleName})"
            isConverted = true
        }

        if (p.isType("String[]")) {
            val countParam = "${p.simpleName}$PARAM_ARR_LEN_POSTFIX"
            getLines += """
                const char* ${p.simpleName}[$countParam];
                for (int i = 0; i < ${p.simpleName}$PARAM_ARR_LEN_POSTFIX; i++) {
                    const jstring str = (jstring)env->GetObjectArrayElement(obj_${p.simpleName}, i);
                    auto rawStr = (char*)env->GetStringUTFChars(str, JNI_FALSE);
                    ${p.simpleName}[i] = rawStr;
                }
            """.trimIndent()
            releaseLines += """
                for (int i = 0; i < $countParam; i++) {
                    const jstring str = (jstring)env->GetObjectArrayElement(obj_${p.simpleName}, i);
                    env->ReleaseStringUTFChars(str, ${p.simpleName}[i]);
                }
            """.trimIndent()
            isConverted = true
        }

        if (p.isType("ImVec2[]")) {
            val lengthVar = "${p.simpleName}Length"
            getLines += """
                int $lengthVar = env->GetArrayLength(obj_${p.simpleName});
                ImVec2 ${p.simpleName}[$lengthVar];
                for (int i = 0; i < $lengthVar; i++) {
                    jobject src = env->GetObjectArrayElement(obj_${p.simpleName}, i);
                    ImVec2 dst;
                    Jni::ImVec2Cpy(env, src, &dst);
                    ${p.simpleName}[i] = dst;
                }
            """.trimIndent()
            isConverted = true
        }

        if (p.isType("ImVec4[]")) {
            val lengthVar = "${p.simpleName}Length"
            getLines += """
                int $lengthVar = env->GetArrayLength(obj_${p.simpleName});
                ImVec4 ${p.simpleName}[$lengthVar];
                for (int i = 0; i < $lengthVar; i++) {
                    jobject src = env->GetObjectArrayElement(obj_${p.simpleName}, i);
                    ImVec4 dst;
                    Jni::ImVec4Cpy(env, src, &dst);
                    ${p.simpleName}[i] = dst;
                }
            """.trimIndent()
            isConverted = true
        }

        fun isAddPrefixType(type: CtTypeReference<*>): Boolean {
            return setOf(
                "String[]",
                "ImVec2[]",
                "ImVec4[]",
            ).contains(type.simpleName)
        }

        if (isConverted && (!mNew.isType("void") || isAddPrefixType(p.type))) {
            p.setSimpleName<Nothing>("obj_" + p.simpleName)
        }
    }

    // Sine we work with an already transformed method, which doesn't contain original information,
    // we need to process the original method instead. For example, ImVec args are transformed into floats.
    mOrig.parameters.forEach { p ->
        // Transformed method may not have the current param. For example, if it's optional.
        // So for ImVec arguments we check, if there is a parameter with the same name and "X" attached.
        // It's a sort of marker in our case.
        if (method.parameters.find { it.simpleName == p.simpleName + "X" } != null) {
            if (p.isType("ImVec2")) {
                getLines += "ImVec2 ${p.simpleName} = ImVec2(${p.simpleName}X, ${p.simpleName}Y)"
            }
            if (p.isType("ImVec4")) {
                getLines += "ImVec4 ${p.simpleName} = ImVec4(${p.simpleName}X, ${p.simpleName}Y, ${p.simpleName}Z, ${p.simpleName}W)"
            }
        }
    }

    if (getLines.isNotEmpty() || releaseLines.isNotEmpty()) {
        val bOrigCode = mNew.body.getLastStatement<CtStatement>().prettyprint()

        mNew.body.statements.clear()

        getLines.forEach {
            mNew.body.addStatement<Nothing>(mNew.factory.createCodeSnippet(it))
        }

        mNew.body.addStatement<Nothing>(mNew.factory.createCodeSnippet(bOrigCode.replace("return ", "auto _result = ")))

        releaseLines.forEach {
            mNew.body.addStatement<Nothing>(mNew.factory.createCodeSnippet(it))
        }

        if (!mNew.isType("void")) {
            mNew.body.addStatement<Nothing>(mNew.factory.createCodeSnippet("return _result"))
        }

        return mNew
    }

    return null
}

private fun CtMethod<*>.prettyprint(isManual: Boolean): String {
    return buildString {
        val str = prettyprint().let {
            if (isManual) {
                it.replaceFirst(" {", "; /*MANUAL")
            } else {
                it.replaceFirst(" {", "; /*")
            }
        }
        append(str)
        lastIndexOf("}").let { idx ->
            replace(idx, idx + 1, "*/")
        }
    }
}

private fun transformMethodToContent(
    mOrig: CtMethod<*>,
    params: List<CtParameter<*>> = emptyList(),
    defaults: IntArray = intArrayOf()
): List<String> {
    fun CtMethod<*>.printJni(): String {
        return convertManualJni(mOrig, this)?.prettyprint(true) ?: prettyprint(false)
    }

    val methods = mutableListOf<String>()
    val mNew = createMethod(mOrig, params, defaults)

    methods += mNew.printJni()

    if (mOrig.isType("ImVec2") || mOrig.isType("ImVec4")) {
        methods += createMethodVecValueReturn("x", mNew, mOrig.simpleName, params, defaults).printJni()
        methods += createMethodVecValueReturn("y", mNew, mOrig.simpleName, params, defaults).printJni()
        if (mOrig.isType("ImVec4")) {
            methods += createMethodVecValueReturn("z", mNew, mOrig.simpleName, params, defaults).printJni()
            methods += createMethodVecValueReturn("w", mNew, mOrig.simpleName, params, defaults).printJni()
        }
    }

    return methods
}

fun jniMethodContent(method: CtMethod<*>): List<String> {
    val methods = mutableListOf<String>()

    for (paramsSize in (findFirstOptParam(method)..method.parameters.size)) {
        val params = method.parameters.subList(0, paramsSize)
        methods += transformMethodToContent(method, params)
    }

    for (defaults in findDefaultsCombinations(method)) {
        methods += transformMethodToContent(method, method.parameters, defaults)
    }

    return methods
}

private fun createFieldGetContent(field: CtField<*>): List<String> {
    val f = field.factory

    val getAccessor = f.createMethod<Nothing>()
    getAccessor.setParent<Nothing>(field.parent)
    getAccessor.setSimpleName<Nothing>("get${field.simpleName}")
    getAccessor.setType<Nothing>(field.type)
    getAccessor.addModifier<Nothing>(ModifierKind.PRIVATE)
    getAccessor.setAnnotations<Nothing>(field.annotations)

    val result = mutableListOf<String>()

    if (field.hasAnnotation(A_NAME_TYPE_ARRAY)) {
        val arrayType = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_TYPE)
        val arraySize = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_SIZE)

        val getArray = getAccessor.clone()
        getArray.addModifier<Nothing>(ModifierKind.NATIVE)
        getArray.setSimpleName<Nothing>("nGet${field.simpleName}")
        getArray.setType<Nothing>(f.createTypeParam("$arrayType[]"))

        when (arrayType) {
            "boolean", "short", "int", "float", "double", "long" -> {
                getArray.setBody<Nothing>(
                    f.createCodeSnippet(
                        """
                    j$arrayType jBuf[$arraySize];
                    for (int i = 0; i < $arraySize; i++)
                        jBuf[i] = $PTR_JNI_THIS->${field.getCallName()}[i];
                    j${arrayType}Array result = env->New${arrayType.capitalize()}Array($arraySize);
                    env->Set${arrayType.capitalize()}ArrayRegion(result, 0, $arraySize, jBuf);
                    return result
                """.trimIndent()
                    )
                )
            }

            "ImVec2", "ImVec4" -> {
                getArray.setBody<Nothing>(
                    f.createCodeSnippet(
                        """
                    return Jni::New${arrayType}Array(env, $PTR_JNI_THIS->${field.getCallName()}, $arraySize)
                """.trimIndent()
                    )
                )
            }
        }

        result += getArray.prettyprint(false)

        when (arrayType) {
            "boolean", "short", "int", "float", "double", "long" -> {
                val getArrayIdx = getArray.clone()
                getArrayIdx.addParameter<Nothing>(f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam("int"))
                    setSimpleName<Nothing>("idx")
                })
                getArrayIdx.setType<Nothing>(f.createTypeParam(arrayType))
                getArrayIdx.setBody<Nothing>(f.createCodeSnippet("return $PTR_JNI_THIS->${field.getCallName()}[idx]"))

                result += getArrayIdx.prettyprint(false)
            }
        }
    } else {
        getAccessor.addAnnotation<Nothing>(f.createAnnotation(f.createTypeReference<Nothing?>().apply {
            setSimpleName<Nothing>("imgui.binding.annotation.BindingMethod")
        }).apply {
            addValue<Nothing>(A_VALUE_CALL_NAME, field.getCallName())
        })

        result += transformMethodToContent(getAccessor).map {
            it.replace("$PTR_JNI_THIS->${getAccessor.simpleName}()", "$PTR_JNI_THIS->${field.getCallName()}")
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
    setAccessor.addModifier<Nothing>(ModifierKind.PRIVATE)

    val valueParam = f.createParameter<Nothing>().apply {
        setType<Nothing>(field.type)
        setSimpleName<Nothing>("value")
    }

    // Add this param to the method placeholder, since some transformations can check it.
    setAccessor.addParameter<Nothing>(valueParam)

    val result = mutableListOf<String>()

    if (field.hasAnnotation(A_NAME_TYPE_ARRAY)) {
        val arrayType = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_TYPE)
        val arraySize = field.getAnnotation(A_NAME_TYPE_ARRAY)!!.getValueAsString(A_VALUE_SIZE)

        val setArray = setAccessor.clone()
        setArray.addModifier<Nothing>(ModifierKind.NATIVE)
        setArray.setSimpleName<Nothing>("nSet${field.simpleName}")

        when (arrayType) {
            "boolean", "short", "int", "float", "double", "long" -> {
                setArray.setBody<Nothing>(
                    f.createCodeSnippet(
                        """
                    for (int i = 0; i < $arraySize; i++)
                        $PTR_JNI_THIS->${field.getCallName()}[i] = value[i]
                """.trimIndent()
                    )
                )
            }

            "ImVec2", "ImVec4" -> {
                setArray.setBody<Nothing>(
                    f.createCodeSnippet("""
                        Jni::${arrayType}ArrayCpy(env, value, $PTR_JNI_THIS->${field.getCallName()}, $arraySize)
                    """.trimIndent()
                    )
                )
            }
        }

        result += setArray.prettyprint(false)

        when (arrayType) {
            "boolean", "short", "int", "float", "double", "long" -> {
                val setArrayIdx = setArray.clone()
                setArrayIdx.parameters.clear()
                setArrayIdx.addParameter<Nothing>(f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam("int"))
                    setSimpleName<Nothing>("idx")
                })
                setArrayIdx.addParameter<Nothing>(f.createParameter<Nothing>().apply {
                    setType<Nothing>(f.createTypeParam(arrayType))
                    setSimpleName<Nothing>("value")
                })
                setArrayIdx.setType<Nothing>(f.createTypeParam(arrayType))
                setArrayIdx.setBody<Nothing>(f.createCodeSnippet("$PTR_JNI_THIS->${field.getCallName()}[idx] = value"))

                result += setArrayIdx.prettyprint(false)
            }
        }
    } else {
        result += transformMethodToContent(setAccessor, listOf(valueParam)).map {
            it.replace(
                "$PTR_JNI_THIS->${setAccessor.simpleName}\\((.+)\\)".toRegex(),
                "$PTR_JNI_THIS->${field.getCallName()} = $1"
            )
        }
    }

    return result
}

fun jniFieldContent(field: CtField<*>): List<String> {
    val fields = mutableListOf<String>()
    val a = field.getAnnotation(A_NAME_BINDING_FIELD)
    if (a?.containsValue(A_VALUE_ACCESSORS, A_TYPE_VALUE_ACCESSOR_GETTER) == true) {
        fields += createFieldGetContent(field)
    }
    if (a?.containsValue(A_VALUE_ACCESSORS, A_TYPE_VALUE_ACCESSOR_SETTER) == true) {
        fields += createFieldSetContent(field)
    }
    return fields
}
