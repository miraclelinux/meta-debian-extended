SUMMARY = "The Xorg font encoding files"

DESCRIPTION = "The encodings that map to specific characters for a \
number of Xorg and common fonts."

require ${COREBASE}/meta/recipes-graphics/xorg-font/xorg-font-common.inc
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING;md5=9da93f2daf2d5572faa2bfaf0dbd9e76"

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/xfonts-encodings.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/encodings-${PV}"
BPN = "xfonts-encodings"
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/xorg-font/encodings"

DEPENDS = "mkfontscale-native mkfontdir-native font-util-native"
RDEPENDS_${PN} = ""

SRC_URI += "file://nocompiler.patch"

inherit allarch

EXTRA_OECONF += "--with-encodingsdir=${datadir}/fonts/X11/encodings"
