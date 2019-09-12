# base recipe: meta/recipes-graphics/xorg-util/makedepend_1.0.5.bb
# base branch: warrior
# base commit: 4ba923dbac2845aca5b4312a5f278ad886a7acc7

SUMMARY = "create dependencies in makefiles"

DESCRIPTION = "The makedepend program reads each sourcefile in sequence \
and parses it like a C-preprocessor, processing \
all #include, #define,  #undef, #ifdef, #ifndef, #endif, #if, #elif \
and #else directives so that it can correctly tell which #include, \
directives would be used in a compilation. Any #include, directives \
can reference files having other #include directives, and parsing will \
occur in these files as well."

HOMEPAGE = "http://www.x.org"
BUGTRACKER = "https://bugs.freedesktop.org/enter_bug.cgi?product=xorg"

SECTION = "x11/utils"
LICENSE = "MIT-X"

DEPENDS = "xorgproto util-macros"
PE = "1"

inherit debian-package
require recipes-debian/sources/xutils-dev.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/xutils-dev-7.7+5"
S = "${DEBIAN_UNPACK_DIR}/makedepend"

# xutils-dev is a collection of sub-packages, and
# each sub-package has its own version.
PV = "1.0.5"

inherit autotools pkgconfig

BBCLASSEXTEND = "native"

LIC_FILES_CHKSUM = "file://COPYING;md5=43a6eda34b48ee821b3b66f4f753ce4f"
