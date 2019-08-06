# base recipe: meta/recipes-support/atk/atk_2.30.0.bb
# base branch: warrior
# base commit: 7e6ad795543a780989f709999a19ca2e86422b67

SUMMARY = "Accessibility toolkit for GNOME"
HOMEPAGE = "http://live.gnome.org/GAP/"
BUGTRACKER = "https://bugzilla.gnome.org/"
SECTION = "x11/libs"

LICENSE = "GPLv2+ & LGPLv2+"
LIC_FILES_CHKSUM = "\
	file://COPYING;md5=3bf50002aefd002f49e7bb854063f7e7 \
	file://atk/atkutil.c;endline=18;md5=6fd31cd2fdc9b30f619ca8d819bc12d3 \
	file://atk/atk.h;endline=18;md5=fcd7710187e0eae485e356c30d1b0c3b \
"

# Need gettext-native as Meson can't turn off i18n
DEPENDS = "gettext-native glib-2.0"

GNOMEBASEBUILDCLASS = "meson"
inherit gnomebase

BPN = "atk1.0"
inherit debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/atk-${PV}"

inherit gtk-doc gettext upstream-version-is-even gobject-introspection

GTKDOC_ENABLE_FLAG = "-Ddocs=true"
GTKDOC_DISABLE_FLAG = "-Ddocs=false"

EXTRA_OEMESON_append_class-target = "\
	${@bb.utils.contains('GTKDOC_ENABLED', 'True', '${GTKDOC_ENABLE_FLAG}', '${GTKDOC_DISABLE_FLAG}', d)} \
"

FILESPATH_append = ":${COREBASE}/meta/recipes-support/atk/atk"
SRC_URI += "file://0001-meson.build-enable-introspection-for-cross-compile.patch"

BBCLASSEXTEND = "native nativesdk"
