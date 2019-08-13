# base recipe: meta/recipes-gnome/gnome/adwaita-icon-theme_3.30.1.bb
# base branch: warrior
# base commit: 2ad84e1296520270caae776fac6f80a958171fa2

SUMMARY = "GTK+ icon theme"
HOMEPAGE = "http://ftp.gnome.org/pub/GNOME/sources/adwaita-icon-theme/"
BUGTRACKER = "https://bugzilla.gnome.org/"
SECTION = "x11/gnome"

LICENSE = "LGPL-3.0 | CC-BY-SA-3.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=c84cac88e46fc07647ea07e6c24eeb7c"

inherit allarch autotools pkgconfig gettext gtk-icon-cache upstream-version-is-even debian-package
require recipes-debian/sources/adwaita-icon-theme.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-gnome/gnome/adwaita-icon-theme"
DEBIAN_QUILT_PATCHES = ""

MAJ_VER = "${@oe.utils.trim_version("${PV}", 2)}"
SRC_URI += "file://0001-Don-t-use-AC_CANONICAL_HOST.patch \
            file://0001-Run-installation-commands-as-shell-jobs.patch \
           "

DEPENDS += "librsvg-native"

PACKAGES = "${PN}-cursors ${PN}-symbolic-hires ${PN}-symbolic ${PN}-hires ${PN}"

RREPLACES_${PN} = "gnome-icon-theme"
RCONFLICTS_${PN} = "gnome-icon-theme"
RPROVIDES_${PN} = "gnome-icon-theme"

FILES_${PN}-cursors = "${prefix}/share/icons/Adwaita/cursors/"
FILES_${PN}-symbolic-hires = "${prefix}/share/icons/Adwaita/96x96/*/*.symbolic.png \
                              ${prefix}/share/icons/Adwaita/64x64/*/*.symbolic.png \
                              ${prefix}/share/icons/Adwaita/48x48/*/*.symbolic.png \
                              ${prefix}/share/icons/Adwaita/32x32/*/*.symbolic.png"
FILES_${PN}-symbolic = "${prefix}/share/icons/Adwaita/16x16/*/*.symbolic.png \
                        ${prefix}/share/icons/Adwaita/24x24/*/*.symbolic.png \
                        ${prefix}/share/icons/Adwaita/scalable/*/*-symbolic*.svg"
FILES_${PN}-hires = "${prefix}/share/icons/Adwaita/256x256/ \
                     ${prefix}/share/icons/Adwaita/512x512/"
FILES_${PN} = "${prefix}/share/icons/Adwaita/ \
               ${prefix}/share/pkgconfig/adwaita-icon-theme.pc"

BBCLASSEXTEND = "native nativesdk"
