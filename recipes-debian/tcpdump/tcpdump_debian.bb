# base recipe: meta-openembedded/meta-networking/recipes-support/tcpdump/tcpdump_4.9.3.bb
# base branch: warrior
# base commit: fea53271d1fcd482ed1003e40f2cf5573cdb37a3

SUMMARY = "A sophisticated network protocol analyzer"
HOMEPAGE = "http://www.tcpdump.org/"
SECTION = "net"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d4b0366557951c84a94fabe3529f867"

DEPENDS = "libpcap"

RDEPENDS_${PN}-ptest += " make perl \
	perl-module-file-basename \
	perl-module-posix \
	perl-module-carp"

inherit debian-package
require recipes-debian/sources/tcpdump.inc

SRC_URI += " \
    file://unnecessary-to-check-libpcap.patch \
    file://avoid-absolute-path-when-searching-for-libdlpi.patch \
    file://add-ptest.patch \
    file://run-ptest \
"

inherit autotools-brokensep ptest

PACKAGECONFIG ?= "openssl"

PACKAGECONFIG[libcap-ng] = "--with-cap-ng,--without-cap-ng,libcap-ng"
PACKAGECONFIG[openssl] = "--with-crypto,--without-openssl --without-crypto,openssl"
PACKAGECONFIG[smi] = "--with-smi,--without-smi,libsmi"
# Note: CVE-2018-10103 (SMB - partially fixed, but SMB printing disabled)
PACKAGECONFIG[smb] = "--enable-smb,--disable-smb"

EXTRA_AUTORECONF += "-I m4"

do_configure_prepend() {
    mkdir -p ${S}/m4
    if [ -f aclocal.m4 ]; then
        mv aclocal.m4 ${S}/m4
    fi
}

do_install_append() {
    # make install installs an unneeded extra copy of the tcpdump binary
    rm -f ${D}${sbindir}/tcpdump.${PV}
}

do_compile_ptest() {
    oe_runmake buildtest-TESTS
}
