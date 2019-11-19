# base recipe: meta/recipes-graphics/mesa/mesa-gl_19.0.8.bb
# base branch: warrior
# base commit: 271c0c2dc1e65f6b32bbc9d0521c627c82cd47b7

BPN = "mesa"

require mesa_debian.bb

SUMMARY += " (OpenGL only, no EGL/GLES)"

PROVIDES = "virtual/libgl virtual/mesa"

PACKAGECONFIG ??= "opengl dri ${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}"
PACKAGECONFIG_class-target = "opengl dri ${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}"
