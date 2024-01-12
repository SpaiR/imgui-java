package tool.generator.api.definition.node.transform.template

import tool.generator.api.definition.dsl.TransformationDsl
import tool.generator.api.definition.node.transform.method.*
import tool.generator.api.definition.node.transform.struct.`handle vec2 return`
import tool.generator.api.definition.node.transform.struct.`handle vec4 return`
import tool.generator.api.definition.node.transform.struct.`remove get and set from autoBody jni`
import tool.generator.api.definition.node.transform.struct.`set body from autoBody jni`

fun TransformationDsl.fieldsTransformationsTemplate(
    jvmAutoBodyThisPointer: String = THIS_PTR_CALL_JVM,
    jniAutoBodyThisPointer: String = THIS_PTR_CALL_JNI,
) {
    transformation {
        chain(
            `set auto body method name from original name`,
        )
    }
    transformation {
        chain(
            `set method public jvm`,
            `set this pointer`(jvmAutoBodyThisPointer),
        )
        chain(
            `set method private jni`,
            `set this pointer`(jniAutoBodyThisPointer),
        )
    }
    transformation {
        chain(
            `set args call for auto body from jvm to jni`,
            `set args call for auto body from jni to native`,
        )
    }
    transformation {
        chain(
            `remove get and set from autoBody jni`,
        )
    }
    transformation {
        chain(
            `handle vec2 arg`,
            `handle vec2 return`,
        )
    }
    transformation {
        chain(
            `handle vec4 arg`,
            `handle vec4 return`,
        )
    }
    transformation { // should be the last in the chain
        chain(
            `add static struct field`,
            `set body from autoBody jvm`,
            `set body from autoBody jni`,
        )
    }
}

