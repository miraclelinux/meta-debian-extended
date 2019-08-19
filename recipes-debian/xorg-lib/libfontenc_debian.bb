# base recipe: meta/recipes-graphics/xorg-lib/libfontenc_1.1.4.bb
# base branch: warrior
# base commit: a1c2b81d79bfd2f0ea12d5dfe21e997e7424d9d8

SUMMARY = "X font encoding library"

DESCRIPTION = "libfontenc is a library which helps font libraries \
portably determine and deal with different encodings of fonts."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libfontenc.inc
DEBIAN_PATCH_TYPE = "nopatch"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=96254c20ab81c63e65b26f0dbcd4a1c1"

DEPENDS += "zlib xorgproto font-util"

BBCLASSEXTEND = "native"
