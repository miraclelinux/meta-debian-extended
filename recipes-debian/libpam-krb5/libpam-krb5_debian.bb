SUMMARY = "A Kerberos PAM module build against the MIT Kerberos libraries."
LICENSE = "(BSD-3-Clause | GPLv1+) & MIT & PD"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=d7bb1e7b55bf8853342cae13ca81c8ab"

# Set DEBIAN_UNPACK_DIR to the path to the extracted Debian source package.
DEBIAN_UNPACK_DIR = "${WORKDIR}/pam-krb5-${PV}"

inherit autotools-brokensep debian-package
require recipes-debian/sources/libpam-krb5.inc

DEPENDS += "libpam libkrb5"
RDEPENDS_${PN} += "krb5-config"
REQUIRED_DISTRO_FEATURES = "pam"

EXTRA_OECONF = " \
                --libdir=${base_libdir} \
               "

FILES_${PN} += "${base_libdir}/security/* ${datadir}/* ${docdir}/*"

do_install_append() {
    install -d ${D}${docdir}/libpam-krb5
    install -m 0644 ${S}/debian/libpam-krb5.NEWS ${D}${docdir}/libpam-krb5/NEWS.Debian
    install -m 0644 ${S}/NEWS ${D}${docdir}/libpam-krb5
    install -m 0644 ${S}/debian/README.Debian ${D}${docdir}/libpam-krb5
    install -m 0644 ${S}/README ${D}${docdir}/libpam-krb5
    install -m 0644 ${S}/TODO ${D}${docdir}/libpam-krb5
    install -m 0644 ${S}/debian/changelog ${D}${docdir}/libpam-krb5/changelog.Debian
    install -m 0644 ${S}/debian/copyright ${D}${docdir}/libpam-krb5

    install -d ${D}${datadir}/pam-configs
    install -m 0644 ${S}/debian/pam-auth-update ${D}${datadir}/pam-configs/krb5
}
