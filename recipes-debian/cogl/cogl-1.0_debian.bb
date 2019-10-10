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

RDEPENDS_${PN}-dev = "libcogl"
