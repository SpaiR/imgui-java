package tool.generator.api.definition.node.type.method.ext

import tool.generator.api.definition.node.container.ListContent
import tool.generator.api.definition.node.container.StringContent
import tool.generator.api.definition.node.type.method.AutoBodyDefinitionNode

/**
 * The value is used before the rendering of the method call.
 * For example, if the method is named `enableButton` when rendered it will look like: `[thisPointer]enableButton()`.
 * So if the content of the property is `this.` method call will look like `this.enableButton()`.
 */
var AutoBodyDefinitionNode.thisPointer: String
    get() = storage.value<StringContent>("thisPointer").value
    set(value) = storage.put("thisPointer", StringContent(value))

/**
 * The value is used when the method call is rendered.
 */
var AutoBodyDefinitionNode.methodName: String
    get() = storage.value<StringContent>("methodName").value
    set(value) = storage.put("methodName", StringContent(value))

/**
 * The value is used during the rendering of the method call as provided args.
 * For example, `this.enable([argsCall])`. So if the content is `num1, str2` we will see `this.enable(num1, str2)`.
 */
var AutoBodyDefinitionNode.argsCall: Collection<String>
    get() = storage.get<ListContent<StringContent>>("argsCall")?.value?.map { it.value } ?: emptyList()
    set(value) = storage.put("argsCall", ListContent(value.map { StringContent(it) }))
