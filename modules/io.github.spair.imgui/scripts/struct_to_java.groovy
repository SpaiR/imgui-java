def inputTxt = new File(args[0]).text
def outputFile = new File(args[1])
def jStructName = args[2] as String

outputFile.text = ''

inputTxt = inputTxt
    .replace('ImGuiID', 'int')
    .replace('bool', 'boolean')
    .replace('char*', 'String')
    .replace('const char*', 'String')

def regex = ~'(\\w+)\\s+(\\w+)(?:.*?//(.+))?'

inputTxt.eachLine { line ->
    def trimmedLine = line.trim()

    if (!trimmedLine.contains(';') || trimmedLine.startsWith('#'))
        return

    def m = regex.matcher(trimmedLine)

    def fieldType
    def fieldName
    def fieldComment

    m.find()

    fieldType = m.group(1)
    fieldName = m.group(2)
    fieldComment = m.group(3)?.trim()

    def javadoc = null

    if (fieldComment) {
        javadoc = """   /**
        |    * $fieldComment
        |    */""".stripMargin()
    }

    def classTxt

    if (fieldType == 'ImVec2') {
        classTxt = """
        |${javadoc ? javadoc : ''}
        |   public native void get${fieldName.capitalize()}(ImVec2 dstImVec2); /*
        |       Jni::ImVec2Cpy(env, &$jStructName->$fieldName, dstImVec2);
        |    */
        |
        |${javadoc ? javadoc : ''}
        |   public native float get${fieldName.capitalize()}X(); /*
        |       return $jStructName->${fieldName}.x;
        |    */
        |
        |${javadoc ? javadoc : ''}
        |   public native float get${fieldName.capitalize()}Y(); /*
        |       return $jStructName->${fieldName}.y;
        |    */
        |
        |${javadoc ? javadoc : ''}
        |   public native void set${fieldName.capitalize()}(float x, float y); /*
        |       $jStructName->${fieldName}.x = x;
        |       $jStructName->${fieldName}.y = y;
        |    */
        """.stripMargin()
    } else {
        classTxt = """
        |${javadoc ? javadoc : ''}
        |   public native $fieldType get${fieldName.capitalize()}(); /*
        |       return $jStructName->$fieldName;
        |    */
        |
        |${javadoc ? javadoc : ''}
        |   public native void set${fieldName.capitalize()}($fieldType ${fieldName.uncapitalize()}); /*
        |       $jStructName->$fieldName = ${fieldName.uncapitalize()};
        |    */
        """.stripMargin()
    }

    outputFile << classTxt
}
