# base recipe: meta/recipes-connectivity/connman/connman-gnome_0.7.bb
# base branch: warrior
# base commit: fdbf32bb766c7e7587782f40df79a1ecbd0fed40

SUMMARY = "GTK+ frontend for the ConnMan network connection manager"
HOMEPAGE = "http://connman.net/"
SECTION = "libs/network"
LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

DEPENDS = "gtk+3 dbus-glib dbus-glib-native intltool-native gettext-native"

BPN = "connman-ui"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-0_20150622+dfsg.orig"

inherit autotools gtk-icon-cache pkgconfig distro_features_check
ANY_OF_DISTRO_FEATURES = "${GTK3DISTROFEATURES}"

RDEPENDS_${PN} = "connman"
FILES_${PN} = "\
	/usr/share/connman_ui_gtk/* \
	/usr/bin/connman-ui-gtk \
"

do_configure_prepend() {
	sed "s/<connman-ui-gtk.h>/\"connman-ui-gtk.h\"/g" -i ${S}/src/*.c
	sed "s/<gtktechnology.h>/\"gtktechnology.h\"/g" -i ${S}/src/*.c
	sed "s/<gtkservice.h>/\"gtkservice.h\"/g" -i ${S}/src/*.c
}
