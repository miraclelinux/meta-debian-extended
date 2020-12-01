# base recipe: meta-oe/recipes-extended/libpwquality/libpwquality_1.4.4.bb
# base branch: master
# base commit: 57a540a541ca4c3890e0b876b2eaad189a2ed51e

DESCRIPTION = "Library for password quality checking and generating random passwords"
HOMEPAGE = "https://github.com/libpwquality/libpwquality"
SECTION = "devel/lib"
LICENSE = "libpwquality | GPL-2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bd2f1386df813a459a0c34fde676fc2"

SRCNAME = "libpwquality"
SRC_URI += "file://add-missing-python-include-dir-for-cross.patch"

UPSTREAM_CHECK_URI = "https://github.com/libpwquality/libpwquality/releases"

S = "${WORKDIR}/${SRCNAME}-${PV}"

DEPENDS = "cracklib virtual/gettext"

inherit debian-package autotools distutils3-base gettext
require recipes-debian/sources/libpwquality.inc

DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

B = "${S}"

export PYTHON_DIR
export BUILD_SYS
export HOST_SYS

EXTRA_OECONF += "--with-python-rev=${PYTHON_BASEVERSION} \
                 --with-python-binary=${STAGING_BINDIR_NATIVE}/${PYTHON_PN}-native/${PYTHON_PN} \
                 --with-pythonsitedir=${PYTHON_SITEPACKAGES_DIR} \
                 --libdir=${base_libdir} \
"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam', '', d)}"
PACKAGECONFIG[pam] = "--enable-pam, --disable-pam, libpam"

do_install_append() {
	mv ${D}/${base_libdir}/pkgconfig ${D}/${libdir}/pkgconfig
}

FILES_${PN} += "${base_libdir}/security/pam_pwquality.so"
FILES_${PN}-dbg += "${base_libdir}/security/.debug"
FILES_${PN}-staticdev += "${base_libdir}/security/pam_pwquality.a"
FILES_${PN}-dev += "${base_libdir}/security/pam_pwquality.la ${libdir}/pkgconfig/pwquality.pc"

