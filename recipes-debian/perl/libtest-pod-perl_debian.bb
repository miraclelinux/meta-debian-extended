SUMMARY = "Perl module to test for POD errors"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=4882c12b205d98a5e772c0fe67df2dcf"

DEPENDS += "perl"

inherit debian-package
require recipes-debian/sources/libtest-pod-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Test-Pod-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "libtest-needs-perl perl-module-test-more"

BBCLASSEXTEND = "native"
