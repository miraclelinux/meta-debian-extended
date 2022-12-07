SUMMARY = "This package provides /etc/krb5.conf"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=b83a9a12562a4ee4582a554747a6c49a"

BPN = "kerberos-configs"

inherit debian-package
require recipes-debian/sources/kerberos-configs.inc

FILES_${PN} += "${sysconfdir}/* ${datadir}/* ${docdir}/*"

do_install() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${S}/krb5.conf.template ${D}${sysconfdir}/krb5.conf
    install -d ${D}${datadir}/kerberos-configs
    install -m 0644 ${S}/krb5.conf.template ${D}${datadir}/kerberos-configs

    install -d ${D}${docdir}/krb5-config
    install -m 0644 ${S}/debian/NEWS ${D}${docdir}/krb5-config/NEWS.Debian
    install -m 0644 ${S}/README ${D}${docdir}/krb5-config
    install -m 0644 ${S}/debian/changelog ${D}${docdir}/krb5-config
    install -m 0644 ${S}/debian/copyright ${D}${docdir}/krb5-config
    install -m 0644 ${S}/packaging-guidelines ${D}${docdir}/krb5-config
}
