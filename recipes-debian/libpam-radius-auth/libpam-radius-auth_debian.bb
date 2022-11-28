SUMMARY = "This is the PAM to RADIUS authentication module."
LICENSE = "GPLv2+ & Livingston"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=fe546348781fcbc12a5e7b8ae6414e60"
NO_GENERIC_LICENSE[Livingston] = "debian/copyright"

# Set DEBIAN_UNPACK_DIR to the path to the extracted Debian source package.
DEBIAN_UNPACK_DIR = "${WORKDIR}/pam_radius-release_1_4_0"

inherit autotools-brokensep debian-package
require recipes-debian/sources/libpam-radius-auth.inc

DEPENDS += "libpam"
REQUIRED_DISTRO_FEATURES = "pam"

FILES_${PN} += "${base_libdir}/security/* ${sysconfdir}/* ${datadir}/* ${docdir}/*"

do_install() {
    install -d ${D}${sysconfdir}
    install -m 600 ${S}/pam_radius_auth.conf ${D}${sysconfdir}
    install -d ${D}${base_libdir}/security
    install -m 755 ${S}/pam_radius_auth.so ${D}${base_libdir}/security

    install -d ${D}${docdir}/libpam-radius-auth/examples
    install -m 644 ${S}/debian/README.Debian ${D}${docdir}/libpam-radius-auth
    install -m 644 ${S}/README.rst ${D}${docdir}/libpam-radius-auth
    install -m 644 ${S}/USAGE ${D}${docdir}/libpam-radius-auth
    install -m 644 ${S}/debian/changelog ${D}${docdir}/libpam-radius-auth/changelog.Debian
    install -m 644 ${S}/Changelog ${D}${docdir}/libpam-radius-auth/changelog
    install -m 644 ${S}/debian/copyright ${D}${docdir}/libpam-radius-auth
    install -m 644 ${S}/debian/index.html ${D}${docdir}/libpam-radius-auth

    install -m 644 ${S}/debian/pam_example ${D}${docdir}/libpam-radius-auth/examples
    install -m 644 ${S}/pam_radius_auth.conf ${D}${docdir}/libpam-radius-auth/examples
}
