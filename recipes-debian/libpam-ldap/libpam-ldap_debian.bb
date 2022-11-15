SUMMARY = "Pluggable Authentication Module for LDAP"
LICENSE = "GPLv2+ & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=f2f48f06e8b8d7441e01eee2d6f5e8f3"

# Set DEBIAN_UNPACK_DIR to the path to the extracted Debian source package.
DEBIAN_UNPACK_DIR = "${WORKDIR}/pam_ldap-${PV}"

inherit autotools-brokensep debian-package
require recipes-debian/sources/libpam-ldap.inc

DEPENDS += "libpam openldap"
REQUIRED_DISTRO_FEATURES = "pam"

SRC_URI += " \
            file://vers_string.patch \
           "

EXTRA_OECONF = " \
                --libdir=${base_libdir} \
               "

FILES_${PN} += "${base_libdir}/security/* ${datadir}/* ${docdir}/*"

do_install_append() {
    install -d ${D}${docdir}/libpam-ldap
    install -m 644 ${S}/ldapns.schema ${D}${docdir}/libpam-ldap
    install -m 644 ${S}/debian/LDAP-Permissions.txt ${D}${docdir}/libpam-ldap
    install -m 644 ${S}/debian/README.Debian ${D}${docdir}/libpam-ldap
    install -m 644 ${S}/debian/changelog ${D}${docdir}/libpam-ldap/changelog.Debian
    install -m 644 ${S}/ChangeLog ${D}${docdir}/libpam-ldap/changelog
    install -m 644 ${S}/debian/copyright ${D}${docdir}/libpam-ldap

    install -d ${D}${docdir}/libpam-ldap/examples
    install -m 755 ${S}/chfn ${D}${docdir}/libpam-ldap/examples
    install -m 755 ${S}/chsh ${D}${docdir}/libpam-ldap/examples
    install -m 644 ${S}/pam.conf ${D}${docdir}/libpam-ldap/examples
    cp -r --no-preserve=ownership ${S}/pam.d ${D}${docdir}/libpam-ldap/examples

    install -d ${D}${datadir}/libpam-ldap
    install -m 644 ${S}/ldap.conf ${D}${datadir}/libpam-ldap
    install -d ${D}${datadir}/pam-configs
    install -m 644 ${S}/debian/libpam-ldap.pam-auth-update ${D}${datadir}/pam-configs/ldap

    ln -sf pam_ldap.5 ${D}${mandir}/man5/pam_ldap.conf.5
}
