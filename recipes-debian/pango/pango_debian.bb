#
# base recipe: meta/recipes-graphics/pango/pango_1.42.4.bb
# base branch: warrior
# base commit: b6553880223c19b2049e77569c3a9840bdd90727
#

SUMMARY = "Framework for layout and rendering of internationalized text"
DESCRIPTION = "Pango is a library for laying out and rendering of text, \
with an emphasis on internationalization. Pango can be used anywhere \
that text layout is needed, though most of the work on Pango so far has \
been done in the context of the GTK+ widget toolkit. Pango forms the \
core of text and font handling for GTK+-2.x."
HOMEPAGE = "http://www.pango.org/"
BUGTRACKER = "http://bugzilla.gnome.org"
SECTION = "libs"
LICENSE = "LGPLv2.0+"

LIC_FILES_CHKSUM = "file://COPYING;md5=3bf50002aefd002f49e7bb854063f7e7"

GNOMEBASEBUILDCLASS = "meson"

inherit gnomebase gtk-doc ptest-gnome upstream-version-is-even gobject-introspection

SRC_URI = ""
inherit debian-package
require recipes-debian/sources/pango1.0.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/pango/pango/"

SRC_URI += "file://run-ptest \
            file://insensitive-diff.patch"

DEPENDS = "glib-2.0 glib-2.0-native fontconfig freetype virtual/libiconv cairo harfbuzz fribidi"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}"
PACKAGECONFIG[x11] = ",,virtual/libx11 libxft"

GTKDOC_ENABLE_FLAG = "-Denable_docs=true"
GTKDOC_DISABLE_FLAG = "-Denable_docs=false"
EXTRA_OEMESON_append_class-target = " ${@bb.utils.contains('GTKDOC_ENABLED', 'True', '${GTKDOC_ENABLE_FLAG}', \
                                                                                     '${GTKDOC_DISABLE_FLAG}', d)} "
GIR_MESON_OPTION = 'gir'

LEAD_SONAME = "libpango-1.0*"
LIBV = "1.8.0"

FILES_${PN} = "${bindir}/* ${libdir}/libpango*${SOLIBS}"
FILES_${PN}-dev += "${libdir}/pango/${LIBV}/modules/*.la"

RDEPENDS_${PN}-ptest += "liberation-fonts cantarell-fonts"

RPROVIDES_${PN} += "pango-modules pango-module-indic-lang \
                    pango-module-basic-fc pango-module-arabic-lang"

BBCLASSEXTEND = "native nativesdk"

do_install_append () {
	if [ "${PTEST_ENABLED}" != "1" ]; then
		rm -rf ${D}${libexecdir}/installed-tests ${D}${datadir}/installed-tests
                rmdir --ignore-fail-on-non-empty ${D}${libexecdir} ${D}${datadir}
	fi
}
