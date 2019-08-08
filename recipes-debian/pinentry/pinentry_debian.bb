# base recipe: meta/recipes-support/pinentry/pinentry_1.1.0.bb
# base branch: warrior
# base commit: b1b506a8108474136959cb6a630eb6ec0ac0f59e

SUMMARY = "Collection of simple PIN or passphrase entry dialogs"
DESCRIPTION = "\
	Pinentry is a collection of simple PIN or passphrase entry dialogs which \
	utilize the Assuan protocol as described by the aegypten project; see \
	http://www.gnupg.org/aegypten/ for details."

HOMEPAGE = "http://www.gnupg.org/related_software/pinentry/index.en.html"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=cbbd794e2a0a289b9dfcc9f513d1996e"

DEPENDS = "gettext-native libassuan libgpg-error"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

UPSTREAM_CHECK_URI = "https://gnupg.org/download/index.html"
FILESPATH_append = ":${COREBASE}/meta/recipes-support/${BPN}/${BPN}-1.1.0"
SRC_URI += "\
    file://libassuan_pkgconf.patch \
    file://gpg-error_pkconf.patch \
"

inherit autotools pkgconfig

PACKAGECONFIG ??= "ncurses libcap"

PACKAGECONFIG[ncurses] = "--enable-ncurses  --with-ncurses-include-dir=${STAGING_INCDIR}, --disable-ncurses, ncurses"
PACKAGECONFIG[libcap] = "--with-libcap, --without-libcap, libcap"
PACKAGECONFIG[qt] = "--enable-pinentry-qt, --disable-pinentry-qt, qt4-x11"
PACKAGECONFIG[gtk2] = "--enable-pinentry-gtk2, --disable-pinentry-gtk2, gtk+ glib-2.0"

#To use libsecret, add meta-gnome
PACKAGECONFIG[secret] = "--enable-libsecret, --disable-libsecret, libsecret"

EXTRA_OECONF = " \
    --disable-rpath \
    --disable-pinentry-qt5 \
"

BBCLASSEXTEND = "native"
