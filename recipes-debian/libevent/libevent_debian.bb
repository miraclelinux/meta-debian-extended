# base recipe: meta/recipes-support/libevent/libevent_2.1.8.bb
# base branch: warrior
# base commit: 4e479cee5d87155e2a90801496033f1a03c5ab30

SUMMARY = "An asynchronous event notification library"
HOMEPAGE = "http://libevent.org/"
BUGTRACKER = "https://github.com/libevent/libevent/issues"
SECTION = "libs"

LICENSE = "BSD & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=17f20574c0b154d12236d5fbe964f549"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-support/${BPN}/${BPN}"
SRC_URI += "\
    file://Makefile-missing-test-dir.patch \
    file://0001-test-fix-32bit-linux-regress.patch \
    file://run-ptest \
"

UPSTREAM_CHECK_URI = "http://libevent.org/"

PACKAGECONFIG ??= ""
PACKAGECONFIG[openssl] = "--enable-openssl,--disable-openssl,openssl"

inherit autotools

# Needed for Debian packaging
LEAD_SONAME = "libevent-2.1.so"

inherit ptest multilib_header

DEPENDS = "zlib"

BBCLASSEXTEND = "native nativesdk"

do_install_append() {
        oe_multilib_header event2/event-config.h
}

do_install_ptest() {
	install -d ${D}${PTEST_PATH}/test
	for file in ${B}/test/.libs/regress ${B}/test/.libs/test*
	do
		install -m 0755 $file ${D}${PTEST_PATH}/test
	done
}
