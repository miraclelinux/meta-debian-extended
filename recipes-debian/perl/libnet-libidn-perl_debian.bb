SUMMARY = "Perl bindings for GNU Libidn"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=0f6c82cc8795bc88c8b8eb0faf6e6bf9"

DEPENDS += "perl libidn"
RDEPENDS_${PN} += "perl libidn"

inherit debian-package
require recipes-debian/sources/libnet-libidn-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/${PN}-${PV}.ds.orig"

inherit cpan

BBCLASSEXTEND = "native"

EXTRA_CPANFLAGS = "--with-libidn=${STAGING_BASELIBDIR} --with-libidn-inc=${STAGING_INCDIR}"

do_configure_prepend() {
    # skip test to detect libidn or libidn2
    sed -i -e 's|CheckLibidn($Params{INC}, $Params{LIBS})|1|' ${S}/Makefile.PL
}
