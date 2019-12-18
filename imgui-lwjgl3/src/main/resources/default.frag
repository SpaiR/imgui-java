#version 130

#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D Texture;
in vec2 Frag_UV;
in vec4 Frag_Color;

void main()
{
    gl_FragColor = Frag_Color * texture2D(Texture, Frag_UV.st);
}
