# base recipe: meta/recipes-graphics/xorg-lib/xkeyboard-config_2.26.bb
# base branch: warrior
# base commit: 550ed6ba7e43ba7b33e2a1a91c2b8b34d4802b3e

SUMMARY = "Keyboard configuration database for X Window"

DESCRIPTION = "The non-arch keyboard configuration database for X \
Window.  The goal is to provide the consistent, well-structured, \
frequently released open source of X keyboard configuration data for X \
Window System implementations.  The project is targeted to XKB-based \
systems."

HOMEPAGE = "http://freedesktop.org/wiki/Software/XKeyboardConfig"
BUGTRACKER = "https://bugs.freedesktop.org/enter_bug.cgi?product=xkeyboard-config"

LICENSE = "MIT & MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=0e7f21ca7db975c63467d2e7624a12f9"

inherit debian-package
require recipes-debian/sources/xkeyboard-config.inc
DEBIAN_PATCH_TYPE = "quilt"

SECTION = "x11/libs"
DEPENDS = "intltool-native util-macros libxslt-native"

EXTRA_OECONF = "--with-xkb-rules-symlink=xorg --disable-runtime-deps"

FILES_${PN} += "${datadir}/X11/xkb"

inherit autotools pkgconfig gettext

do_install_append () {
    install -d ${D}${datadir}/X11/xkb/compiled
    cd ${D}${datadir}/X11/xkb/rules && ln -sf base xorg
}
