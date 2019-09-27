SUMMARY = "locales utils -- locale-gen, update-locale, validlocale"
LICENSE = "LGPL-2.1"

LIC_FILES_CHKSUM = "file://LICENSES;md5=cfc0ed77a9f62fa62eded042ebe31d72 \
      file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
      file://posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
      file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

inherit debian-package
require recipes-debian/sources/glibc.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/glibc-${PV}"

RDEPENDS_${PN} = "perl \
                  perl-module-getopt-long \
                  perl-module-posix \
                  perl-module-carp \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}/${sbindir}
    install -m 0755 ${S}/debian/local/usr_sbin/* ${D}/${sbindir}

    install -d ${D}/${datadir}/locale/
    install -m 0644 ${S}/intl/locale.alias ${D}/${datadir}/locale/

    install -d ${D}/${libdir}/locale
}

FILES_${PN} += "${libdir}/locale"
