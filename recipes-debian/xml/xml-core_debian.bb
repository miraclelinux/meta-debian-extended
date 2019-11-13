SUMMARY = "XML infrastructure and XML catalog file support"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=786662a617fe8fe5f955258d11c2b135"

inherit debian-package
require recipes-debian/sources/xml-core.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

do_configure[noexec] = "1"

inherit allarch

do_install() {
    oe_runmake "DESTDIR=${D}" install

    rm ${D}${bindir}/dh_installxmlcatalogs
    rmdir --ignore-fail-on-non-empty ${D}${bindir}
    rm -rf ${D}${datadir}/perl5
    rm -rf ${D}${datadir}/debhelper
    rm ${D}${mandir}/man1/dh_installxmlcatalogs.1
}

FILES_${PN} += " \
    /usr/local/share/xml/* \
    /usr/share/xml/* \
"

RDEPENDS_${PN} = "sgml-base"
