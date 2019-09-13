# base recipe: meta-openembedded/meta-oe/recipes-support/libusb/libusb-compat_0.1.5.bb
# base branch: master
# base commit: d00b7bd416eceb4d6aa70a572832a05d6f9870c0

SUMMARY = "libusb-0.1 compatibility layer for libusb1"
DESCRIPTION = "libusb-0.1 compatible layer for libusb1, a drop-in replacement \
that aims to look, feel and behave exactly like libusb-0.1"
HOMEPAGE = "http://www.libusb.org/"
BUGTRACKER = "http://www.libusb.org/report"
SECTION = "libs"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f2ac5f3ac4835e8f91324a26a590a423"
DEPENDS = "libusb1"

inherit debian-package
require recipes-debian/sources/libusb.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/libusb-${PV}"

# Few packages are known not to work with libusb-compat (e.g. libmtp-1.0.0),
# so here libusb-0.1 is removed completely instead of adding virtual/libusb0.
# Besides, libusb-0.1 uses a per 1ms polling that hurts a lot to power
# consumption.
PROVIDES = "libusb virtual/libusb0"
BBCLASSEXTEND = "native nativesdk"

PE = "1"

BINCONFIG = "${bindir}/libusb-config"

inherit autotools pkgconfig binconfig-disabled lib_package

EXTRA_OECONF = "--libdir=${base_libdir}"

do_install_append() {
	install -d ${D}${libdir}
	if [ ! ${D}${libdir} -ef ${D}${base_libdir} ]; then
		mv ${D}${base_libdir}/pkgconfig ${D}${libdir}
	fi
}
