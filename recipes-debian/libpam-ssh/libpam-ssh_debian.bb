SUMMARY = "This pluggable authentication module (PAM) provides single sign-on using secure shell (SSH) keys."
LICENSE = "BSD-2-Clause & BSD-3-Clause & BSD-4-Clause & ISC & PD & AdHoc-0 & GPLv3+"
LIC_FILES_CHKSUM = "file://debian/copyright;md5=2c2b791fb7b7c338bb5dd9c0d2216074"
NO_GENERIC_LICENSE[AdHoc-0] = "debian/copyright"

# Set DEBIAN_UNPACK_DIR to the path to the extracted Debian source package.
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}+ds"

inherit autotools-brokensep debian-package
require recipes-debian/sources/libpam-ssh.inc

DEPENDS += "libpam openssh openssl"
RDEPENDS_${PN} += "openssh-misc"
REQUIRED_DISTRO_FEATURES = "pam"

# remove unnecessary parts for cross-compiling.
SRC_URI += " \
            file://configure.ac.patch \
           "

# PAM modules directory is */security.
TARGET_CPPFLAGS += "-I${STAGING_INCDIR}/security"
FILES_${PN} += "${base_libdir}/security/* ${datadir}/*"

# ssh-agent is included in openssh-misc.
EXTRA_OECONF = " \
                --with-pam-dir=${base_libdir}/security \
                PATH_SSH_AGENT=/usr/bin/ssh-agent \
               "

do_install_append() {
    install -d ${D}${docdir}/libpam-ssh
    install -m 644 ${S}/debian/NEWS.Debian ${D}${docdir}/libpam-ssh
    install -m 644 ${S}/NEWS ${D}${docdir}/libpam-ssh
    install -m 644 ${S}/debian/README.Debian ${D}${docdir}/libpam-ssh
    install -m 644 ${S}/debian/changelog ${D}${docdir}/libpam-ssh/changelog.Debian
    install -m 644 ${S}/ChangeLog ${D}${docdir}/libpam-ssh/changelog
    install -m 644 ${S}/debian/copyright ${D}${docdir}/libpam-ssh

    install -d ${D}${datadir}/lintian/overrides
    install -m 644 ${S}/debian/libpam-ssh.lintian-overrides ${D}${datadir}/lintian/overrides/libpam-ssh

    install -d ${D}${datadir}/pam-configs
    install -m 644 ${S}/debian/pam-configs/ssh ${D}${datadir}/pam-configs
}
