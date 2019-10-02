SUMMARY = "Perl bindings for GNU Libidn"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://debian/copyright;md5=0f6c82cc8795bc88c8b8eb0faf6e6bf9"

DEPENDS += "perl libidn-native"
RDEPENDS_${PN} += "libidn"

inherit debian-package
require recipes-debian/sources/libnet-libidn-perl.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/${PN}-${PV}.ds.orig"

inherit cpan

BBCLASSEXTEND = "native"

EXTRA_CPANFLAGS = "--with-libidn=${STAGING_LIBDIR_NATIVE} --with-libidn-inc=${STAGING_INCDIR_NATIVE}"
