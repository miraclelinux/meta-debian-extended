# base recipe: meta/recipes-graphics/xorg-util/util-macros_1.19.2.bb
# base branch: warrior
# base commit: 40a2c320f99a3e2f3b6c7c0ae2398aee1530fcee

SUMMARY = "X autotools macros"

DESCRIPTION = "M4 autotools macros used by various X.org programs."

LICENSE = "MIT & MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=1970511fddd439b07a6ba789d28ff662"

PE = "1"

inherit debian-package
require recipes-debian/sources/xutils-dev.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/xutils-dev-7.7+5"
S = "${DEBIAN_UNPACK_DIR}/util-macros"

# xutils-dev is a collection of sub-packages, and
# each sub-package has its own version.
PV = "1.19.0"

inherit autotools pkgconfig

# ${PN} is empty so we need to tweak -dev and -dbg package dependencies
RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "${PN}-dev (= ${EXTENDPKGV})"

BBCLASSEXTEND = "native nativesdk"
