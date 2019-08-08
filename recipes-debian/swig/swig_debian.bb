# base recipe: meta/recipes-devtools/swig/swig_3.0.12.bb
# base branch: warrior
# base commit: 38d5c8ea98cfa49825c473eba8984c12edf062be

require ${COREBASE}/meta/recipes-devtools/${BPN}/${BPN}.inc

# clean SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/swig.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/swig/swig"

SRC_URI += "file://0001-Use-proc-self-exe-for-swig-swiglib-on-non-Win32-plat.patch \
            file://0001-configure-use-pkg-config-for-pcre-detection.patch \
            file://0001-Add-Node-7.x-aka-V8-5.2-support.patch \
            file://0001-Fix-generated-code-for-constant-expressions-containi.patch \
           "
