# base recipe: meta/recipes-gnome/hicolor-icon-theme/hicolor-icon-theme_0.17.bb
# base branch: warrior
# base commit: 38d5c8ea98cfa49825c473eba8984c12edf062be

SUMMARY = "Default icon theme that all icon themes automatically inherit from"
HOMEPAGE = "http://icon-theme.freedesktop.org/wiki/HicolorTheme"
BUGTRACKER = "https://bugs.freedesktop.org/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=f08a446809913fc9b3c718f0eaea0426"

inherit allarch autotools debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_QUILT_PATCHES = ""

FILES_${PN} += "${datadir}/icons"

BBCLASSEXTEND = "native nativesdk"
