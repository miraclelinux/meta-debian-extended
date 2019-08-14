# base recipe: meta/recipes-connectivity/connman/connman-gnome_0.7.bb
# base branch: warrior
# base commit: 389cfd24be7bad870a85181c14807b7d04711cfc

SUMMARY = "GTK+ frontend for the ConnMan network connection manager"
HOMEPAGE = "http://connman.net/"
SECTION = "libs/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "gtk+3 dbus-glib dbus-glib-native intltool-native gettext-native"

inherit gnomebase

BPN = "connman-gtk"
inherit debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-master"

inherit meson gtk-icon-cache pkgconfig distro_features_check
ANY_OF_DISTRO_FEATURES = "${GTK3DISTROFEATURES}"

RDEPENDS_${PN} = "connman"
