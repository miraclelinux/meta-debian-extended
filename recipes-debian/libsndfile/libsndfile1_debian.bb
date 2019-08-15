# base recipe: meta/recipes-multimedia/libsndfile/libsndfile1_1.0.28.bb
# base branch: warrior
# base commit: 49d6cd5000e2ff77b48b930fbceaa5938095e96d

SUMMARY = "Audio format Conversion library"
HOMEPAGE = "http://www.mega-nerd.com/libsndfile"
AUTHOR = "Erik de Castro Lopo"
DEPENDS = "flac libogg libvorbis sqlite3"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1"

inherit debian-package
require recipes-debian/sources/libsndfile.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/libsndfile-${PV}"
FILESEXTRAPATHS_prepend := "${THISDIR}/libsndfile:"

SRC_URI += "file://CVE-2018-19432.patch"

# alrady applied by Debian
# file://CVE-2017-6892.patch
# file://CVE-2017-8361-8365.patch
# file://CVE-2017-8362.patch
# file://CVE-2017-8363.patch
# file://CVE-2017-14634.patch
# file://CVE-2018-13139.patch
# file://0001-a-ulaw-fix-multiple-buffer-overflows-432.patch
# file://CVE-2017-12562.patch
# file://CVE-2018-19758.patch

LIC_FILES_CHKSUM = "file://COPYING;md5=e77fe93202736b47c07035910f47974a"

CVE_PRODUCT = "libsndfile"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'alsa', d)}"
PACKAGECONFIG[alsa] = "--enable-alsa,--disable-alsa,alsa-lib"

inherit autotools lib_package pkgconfig

do_configure_prepend_arm() {
	export ac_cv_sys_largefile_source=1
	export ac_cv_sys_file_offset_bits=64
}

