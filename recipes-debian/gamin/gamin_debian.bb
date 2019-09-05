# base recipe: poky/meta/recipes-extended/gamin/gamin_0.1.10.bb
# base branch: warrior
# base commit: 1c5702f7abb28efe7453b524e8fa4711ad298f40

SUMMARY = "Gamin the File Alteration Monitor"
DESCRIPTION = "Gamin is a file and directory monitoring system defined to \
be a subset of the FAM (File Alteration Monitor) system."
HOMEPAGE = "http://people.gnome.org/~veillard/gamin/"

LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=412a9be54757a155d0b997b52b519f62"

DEPENDS = "glib-2.0"
PROVIDES = "fam"
PR = "r5"

inherit debian-package
require recipes-debian/sources/gamin.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}"

SRC_URI += "file://no-abstract-sockets.patch \
            file://obsolete_automake_macros.patch \
           "

inherit autotools pkgconfig

EXTRA_OECONF = "--without-python"

PACKAGES += "lib${BPN} lib${BPN}-dev"
FILES_${PN} = "${libexecdir}"
FILES_${PN}-dbg += "${libexecdir}/.debug"
FILES_lib${BPN} = "${libdir}/lib*.so.*"
FILES_lib${BPN}-dev = "${includedir} ${libdir}/pkgconfig ${libdir}/lib*.la \
                      ${libdir}/lib*.a ${libdir}/lib*.so"

RDEPENDS_lib${BPN} = "${PN}"

LEAD_SONAME = "libgamin-1.so"

