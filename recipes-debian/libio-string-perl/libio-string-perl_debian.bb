SUMMARY = "Emulate IO::File interface for in-core strings"
LICENSE = "Artistic-2.0"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=61580cbab9a6b90920a93045d4c7a245"

inherit debian-package
require recipes-debian/sources/libio-string-perl.inc
DEBIAN_QUILT_PATCHES = ""

BBCLASSEXTEND = "native"

# nothing to compile
do_configure[noexec] = "1"
do_compile[noexec] = "1"

SYSROOT_DIRS_NATIVE += "${datadir}"

do_install () {
    install -d ${D}${datadir}/perl5
    mkdir ${D}${datadir}/perl5/IO
    cp -r ${WORKDIR}/IO-String-${PV}/String.pm ${D}${datadir}/perl5/
    cp -r ${WORKDIR}/IO-String-${PV}/String.pm ${D}${datadir}/perl5/IO/
}
