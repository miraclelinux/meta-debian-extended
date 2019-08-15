# base recipe: meta/recipes-support/libcroco/libcroco_0.6.12.bb
# base branch: warrior
# base commit: 0775bf214756d4757196bbb4c80feb3d4347fd64

SUMMARY = "Cascading Style Sheet (CSS) parsing and manipulation toolkit"
HOMEPAGE = "http://www.gnome.org/"
BUGTRACKER = "https://bugzilla.gnome.org/"

LICENSE = "LGPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=55ca817ccb7d5b5b66355690e9abc605 \
                    file://src/cr-rgb.c;endline=22;md5=31d5f0944d556c8589d04ea6055fcc66 \
                    file://tests/cr-test-utils.c;endline=21;md5=2382c27934cae1d3792fcb17a6142c4e"

SECTION = "x11/utils"
DEPENDS = "glib-2.0 libxml2 zlib"
BBCLASSEXTEND = "native nativesdk"
EXTRA_OECONF += "--enable-Bsymbolic=auto"

BINCONFIG = "${bindir}/croco-0.6-config"

inherit gnomebase gtk-doc binconfig-disabled

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libcroco.inc
