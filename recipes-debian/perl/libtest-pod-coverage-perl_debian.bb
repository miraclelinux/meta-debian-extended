SUMMARY = "Perl module to check for pod coverage in a distribution"

SECTION = "libs"
LICENSE = "Artistic-1.0 | Artistic-2.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=eb81408a69bd42bd3917ef43f8a893a1"

DEPENDS += "perl"
RDEPENDS_${PN} += "libpod-coverage-perl"

inherit debian-package
require recipes-debian/sources/libtest-pod-coverage-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/Test-Pod-Coverage-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "libtest-needs-perl perl-module-test-more"

BBCLASSEXTEND = "native"
