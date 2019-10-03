SUMMARY = "drop-in XS replacement for URI::Escape"

HOMEPAGE = "http://search.cpan.org/dist/URI/"
SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=c14677930aae2eb96a54c69cb0e89611"

DEPENDS += "perl libnet-libidn-perl libtest-pod-coverage-perl libtest-pod-perl"
RDEPENDS_${PN} += "libpod-coverage-perl"

inherit debian-package
require recipes-debian/sources/liburi-escape-xs-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/URI-Escape-XS-${PV}"

inherit cpan ptest-perl

RDEPENDS_${PN}-ptest += "libtest-needs-perl perl-module-test-more"

BBCLASSEXTEND = "native"
