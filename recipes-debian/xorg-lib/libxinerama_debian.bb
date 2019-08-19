# base recipe: meta/recipes-graphics/xorg-lib/libxinerama_1.1.4.bb
# base branch: warrior
# base commit: 4e1f9fd4ddff37e6f012c86af7eafdb687f0825d

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

SUMMARY = "Xinerama: Xinerama extension library"

DESCRIPTION = "Xinerama is a simple library designed to interface the \
Xinerama Extension for retrieving information about physical output \
devices which may be combined into a single logical X screen."

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxinerama.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=6f4f634d1643a2e638bba3fcd19c2536 \
                    file://src/Xinerama.c;beginline=2;endline=25;md5=fcef273bfb66339256411dd06ea79c02"

DEPENDS += "libxext xorgproto"
PROVIDES = "xinerama"
XORG_PN = "libXinerama"

BBCLASSEXTEND = "native nativesdk"
