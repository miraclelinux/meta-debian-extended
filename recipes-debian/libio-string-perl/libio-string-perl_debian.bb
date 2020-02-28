SUMMARY = "Emulate IO::File interface for in-core strings"
LICENSE = "Artistic-1.0 | GPL-1.0+"
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/Artistic-1.0;md5=cda03bbdc3c1951996392b872397b798 \
    file://${COMMON_LICENSE_DIR}/GPL-1.0;md5=e9e36a9de734199567a4d769498f743d \
"

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
    cp -r ${WORKDIR}/IO-String-${PV}/String.pm ${D}${datadir}/perl5/IO/
}
