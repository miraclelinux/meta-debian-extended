# base recipe: meta/recipes-graphics/cogl/cogl-1.0_1.22.2.bb
# base branch: warrior
# base commit: bf45a3e222bda60685bf3b04c31401fce37cdd13

require ${COREBASE}/meta/recipes-graphics/cogl/cogl-1.0.inc

BPN = "cogl"
inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/cogl/cogl-1.0"
SRC_URI += "file://test-backface-culling.c-fix-may-be-used-uninitialize.patch"

LIC_FILES_CHKSUM = "file://COPYING;md5=1b1a508d91d25ca607c83f92f3e31c84"

# The following FILES_* fixes build errors like follows:
#
# cogl-1.0-dev : Depends: cogl-1.0 (= 1.22.2-r0)
#                Recommends: libcogl-dev but it is not installable
#                Recommends: libcogl-gles2-dev but it is not installable
#                Recommends: libcogl-pango-dev but it is not installable
#                Recommends: libcogl-path-dev but it is not installable
# E: Unable to correct problems, you have held broken packages.

PACKAGES =+ "libcogl-dev libcogl-gles2-dev libcogl-path-dev libcogl-pango-dev"
DEPENDS_${PN}-examples = "libcogl-dev libcogl-gles2-dev libcogl-pango-dev"

FILES_${PN}-dev = ""
FILES_libcogl-dev = "\
	${includedir}/cogl/cogl \
	${libdir}/pkgconfig/cogl-1.0.pc \
	${libdir}/pkgconfig/cogl-2.0-experimental.pc \
	${libdir}/pkgconfig/cogl-gl-1.0.pc \
	${datadir}/gir-1.0/Cogl-1.0.gir \
	${datadir}/gir-1.0/Cogl-2.0.gir \
	${libdir}/libcogl${SOLIBSDEV} \
"
FILES_libcogl-gles2-dev = "\
	${includedir}/cogl/cogl-gles2 \
	${libdir}/pkgconfig/cogl-gles2-1.0.pc \
	${libdir}/pkgconfig/cogl-gles2-2.0-experimental.pc \
	${libdir}/libcogl-gles2${SOLIBSDEV} \
"
FILES_libcogl-path-dev = "\
	${includedir}/cogl/cogl-path \
	${libdir}/pkgconfig/cogl-path-1.0.pc \
	${libdir}/pkgconfig/cogl-path-2.0-experimental.pc \
	${libdir}/libcogl-path${SOLIBSDEV} \
"
FILES_libcogl-pango-dev = "\
	${includedir}/cogl/cogl-pango \
	${libdir}/pkgconfig/cogl-pango-1.0.pc \
	${libdir}/pkgconfig/cogl-pango-2.0-experimental.pc \
	${datadir}/gir-1.0/CoglPango-1.0.gir \
	${datadir}/gir-1.0/CoglPango-2.0.gir \
	${libdir}/libcogl-pango${SOLIBSDEV} \
"
