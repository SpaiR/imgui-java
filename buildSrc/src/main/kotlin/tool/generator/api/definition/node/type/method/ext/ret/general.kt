package tool.generator.api.definition.node.type.method.ext.ret

enum class ReturnTypeGeneral(val value: String) : ReturnTypeCommon {
    VOID("void"),
    STRING("String"),
    BOOL("boolean"),
    INT("int"),
    FLOAT("float"),
    DOUBLE("double"),
    LONG("long");

    override fun value() = value

    override fun copy() = this
}
