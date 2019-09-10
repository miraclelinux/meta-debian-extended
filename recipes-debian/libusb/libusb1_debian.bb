# base recipe: poky/meta/recipes-support/libusb/libusb1_1.0.22.bb
# base branch: warrior
# base commit: 23eab5d82c04e3f9e90a7e178b8d1535a14377ec


SUMMARY = "Userspace library to access USB (version 1.0)"
HOMEPAGE = "http://libusb.sf.net"
BUGTRACKER = "http://www.libusb.org/report"
SECTION = "libs"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"

BBCLASSEXTEND = "native nativesdk"

inherit debian-package
require recipes-debian/sources/libusb-1.0.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-support/libusb/libusb1"

SRC_URI += " \
           file://no-dll.patch \
           file://run-ptest \
          "

DEBIAN_UNPACK_DIR = "${WORKDIR}/libusb-${PV}"
S = "${WORKDIR}/libusb-${PV}"

inherit autotools pkgconfig ptest

PACKAGECONFIG_class-target ??= "udev"
PACKAGECONFIG[udev] = "--enable-udev,--disable-udev,udev"

EXTRA_OECONF = "--libdir=${base_libdir}"

do_install_append() {
	install -d ${D}${libdir}
	if [ ! ${D}${libdir} -ef ${D}${base_libdir} ]; then
		mv ${D}${base_libdir}/pkgconfig ${D}${libdir}
	fi
}

do_compile_ptest() {                                                             
    oe_runmake -C tests stress                                                   
}                                                                                
                                                                                 
do_install_ptest() {                                                             
    install -m 755 ${B}/tests/.libs/stress ${D}${PTEST_PATH}         
}

FILES_${PN} += "${base_libdir}/*.so.*"

FILES_${PN}-dev += "${base_libdir}/*.so ${base_libdir}/*.la"
