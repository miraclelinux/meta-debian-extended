# base recipe: meta/recipes-gnome/gtk+/gtk+3_3.24.5.bb
# base branch: warrior
# base commit: 86be661fd6ae90abc5108c7cba56bdcefa812eab

require ${COREBASE}/meta/recipes-gnome/gtk+/gtk+3.inc

BPN = "gtk+3.0"
inherit debian-package
require recipes-debian/sources/${BPN}.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/gtk+-${PV}"

FILESPATH_append = ":${COREBASE}/meta/recipes-gnome/gtk+/gtk+3"
SRC_URI += "\
    file://0001-Hardcoded-libtool.patch \
    file://0002-Do-not-try-to-initialize-GL-without-libGL.patch \
    file://0003-Add-disable-opengl-configure-option.patch \
    file://link_fribidi.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://gtk/gtk.h;endline=25;md5=1d8dc0fccdbfa26287a271dce88af737 \
                    file://gdk/gdk.h;endline=25;md5=c920ce39dc88c6f06d3e7c50e08086f2 \
                    file://tests/testgtk.c;endline=25;md5=cb732daee1d82af7a2bf953cf3cf26f1"
