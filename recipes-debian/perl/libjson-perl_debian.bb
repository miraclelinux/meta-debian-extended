# base recipe: meta-rpiexperiences/recipes-perl/libjson-perl/libjson-perl_2.90.bb
# base branch: rocko
# base commit: 484a5ffd119deedf084661fd37abe6bf3cb056c4
DESCRIPTION = "JSON (JavaScript Object Notation) encoder/decoder"

SECTION = "libs"
LICENSE = "Artistic-1.0 | GPL-1.0+"

LIC_FILES_CHKSUM = "file://README;beginline=1584;endline=1585;md5=d41d8cd98f00b204e9800998ecf8427e"

inherit debian-package
require recipes-debian/sources/libjson-perl.inc

DEPENDS += "perl "
RDEPENDS_${PN} = "perl perl-module-base perl-module-overload perl-module-bytes\
				  perl-module-carp perl-module-exporter perl-module-b \
				  perl-module-constant"

DEBIAN_UNPACK_DIR = "${WORKDIR}/JSON-4.02"
DEBIAN_QUILT_PATCHES = ""
S = "${WORKDIR}/JSON-4.02"

EXTRA_CPANFLAGS = "INC=-I${STAGING_INCDIR} LIBS=-L${STAGING_LIBDIR}"

inherit cpan

do_compile() {
    export LIBC="$(find ${STAGING_DIR_TARGET}/${base_libdir}/ -name 'libc-*.so')"
    cpan_do_compile
}


