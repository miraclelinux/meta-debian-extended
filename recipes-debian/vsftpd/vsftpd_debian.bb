# base recipe: meta-networking/recipes-daemons/vsftpd/vsftpd_3.0.3.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8
SUMMARY = "Very Secure FTP server"
HOMEPAGE = "https://security.appspot.com/vsftpd.html"
SECTION = "net"
LICENSE = "GPL-2.0-with-OpenSSL-exception"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6067ad950b28336613aed9dd47b1271 \
                        file://COPYRIGHT;md5=04251b2eb0f298dae376d92454f6f72e \
                        file://LICENSE;md5=654df2042d44b8cac8a5654fc5be63eb"

inherit debian-package
require recipes-debian/sources/vsftpd.inc

DEPENDS = "libcap openssl"

SRC_URI += "file://makefile-destdir.patch \
            file://makefile-libs-and-strip.patch \
            file://change-secure_chroot_dir.patch \
            file://volatiles.99_vsftpd \
            file://0001-vsftpd-allow-syscalls-in-the-seccomp-sandbox.patch \
            ${@bb.utils.contains('DISTRO_FEATURES', 'pam', '', 'file://nopam-with-tcp_wrappers.patch', d)} \
            file://0001-sysdeputil.c-Fix-with-musl-which-does-not-have-utmpx.patch \
            "

PACKAGECONFIG ??= "tcp-wrappers"
PACKAGECONFIG[tcp-wrappers] = ",,tcp-wrappers"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'libpam', '', d)}"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam-plugin-listfile', '', d)}"
RDEPENDS_${PN} += "bash lsb procps"
PAMLIB = "${@bb.utils.contains('DISTRO_FEATURES', 'pam', '-L${STAGING_BASELIBDIR} -lpam', '', d)}"
WRAPLIB = "${@bb.utils.contains('PACKAGECONFIG', 'tcp-wrappers', '-lwrap', '', d)}"
inherit update-rc.d useradd systemd

CONFFILES_${PN} = "${sysconfdir}/vsftpd.conf"
LDFLAGS_append =" -lssl -lcrypto -lcap"
CFLAGS_append_libc-musl = " -D_GNU_SOURCE -include fcntl.h"
EXTRA_OEMAKE = "-e MAKEFLAGS="

do_configure() {
    # Fix hardcoded /usr, /etc, /var mess.
    cat tunables.c|sed s:\"/usr:\"${prefix}:g|sed s:\"/var:\"${localstatedir}:g \
    |sed s:\"/etc:\"${sysconfdir}:g > tunables.c.new
    mv tunables.c.new tunables.c
}

do_compile() {
    oe_runmake "LIBS=-L${STAGING_LIBDIR} -lssl -lcrypto -lcap -lcrypt ${PAMLIB} ${WRAPLIB}"
}

do_install() {
    install -d ${D}${sbindir}
    install -d ${D}${mandir}/man8
    install -d ${D}${mandir}/man5
    oe_runmake 'DESTDIR=${D}' install
    install -d ${D}${sysconfdir}
    install -m 644 ${S}/vsftpd.conf ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/init.d/
    install -m 755 ${S}/debian/vsftpd.init ${D}${sysconfdir}/init.d/vsftpd
    install -d ${D}/${sysconfdir}/default/volatiles
    install -m 644 ${WORKDIR}/volatiles.99_vsftpd ${D}/${sysconfdir}/default/volatiles/99_vsftpd

    if ! test -z "${PAMLIB}" ; then
        install -d ${D}${sysconfdir}/pam.d/
        install -m 644  ${S}/debian/vsftpd.pam ${D}${sysconfdir}/pam.d/vsftpd
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${libdir}/tmpfiles.d
        install -m 644 ${S}/debian/vsftpd.tmpfile ${D}${libdir}/tmpfiles.d/vsftpd.conf
    fi

    install -d ${D}${systemd_unitdir}/system
    install -m 644 ${S}/debian/vsftpd.service ${D}${systemd_unitdir}/system

    install -m 644 ${S}/debian/local/ftpusers ${D}${sysconfdir}

    install -d ${D}${sysconfdir}/logrotate.d
    install -m 644 ${S}/debian/vsftpd.logrotate ${D}${sysconfdir}/logrotate.d/vsftpd

    install -d ${D}${bindir}
    install -m 755 ${S}/debian/local/vsftpdwho ${D}${bindir}

    install -d ${D}${datadir}/apport/package-hooks
    install -m 644 ${S}/debian/vsftpd.apport ${D}${datadir}/apport/package-hooks/vsftpd.py

    install -d ${D}${datadir}/bug
    install -m 755 ${S}/debian/vsftpd.bug-script ${D}${datadir}/bug/vsftpd

    install -d ${D}${docdir}/vsftpd
    install -m 644 ${S}/AUDIT ${D}${docdir}/vsftpd
    install -m 644 ${S}/BENCHMARKS ${D}${docdir}/vsftpd
    install -m 644 ${S}/BUGS ${D}${docdir}/vsftpd
    install -m 644 ${S}/FAQ ${D}${docdir}/vsftpd
    install -m 644 ${S}/debian/vsftpd.NEWS ${D}${docdir}/vsftpd/NEWS.Debian
    install -m 644 ${S}/README ${D}${docdir}/vsftpd
    install -m 644 ${S}/debian/vsftpd.README.Debian ${D}${docdir}/vsftpd/README.Debian
    install -m 644 ${S}/README.security ${D}${docdir}/vsftpd
    install -m 644 ${S}/README.ssl ${D}${docdir}/vsftpd
    install -m 644 ${S}/REWARD ${D}${docdir}/vsftpd
    cp -r --no-preserve=ownership ${S}/SECURITY ${D}${docdir}/vsftpd
    install -m 644 ${S}/SIZE ${D}${docdir}/vsftpd
    install -m 644 ${S}/SPEED ${D}${docdir}/vsftpd
    install -m 644 ${S}/TODO ${D}${docdir}/vsftpd
    install -m 644 ${S}/TUNING ${D}${docdir}/vsftpd
    install -m 644 ${S}/Changelog ${D}${docdir}/vsftpd/changelog
    install -m 644 ${S}/debian/changelog ${D}${docdir}/vsftpd/changelog.Debian
    install -m 644 ${S}/debian/copyright ${D}${docdir}/vsftpd
    cp -r --no-preserve=ownership ${S}/EXAMPLE ${D}${docdir}/vsftpd/examples
}

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "vsftpd"
INITSCRIPT_PARAMS_${PN} = "defaults 80"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM_${PN} = "--system --home-dir /var/lib/ftp --no-create-home -g ftp \
                       --shell /bin/false ftp "
GROUPADD_PARAM_${PN} = "-r ftp"

SYSTEMD_SERVICE_${PN} = "vsftpd.service"

pkg_postinst_${PN}() {
    if [ -z "$D" ]; then
        if type systemd-tmpfiles >/dev/null; then
            systemd-tmpfiles --create
        elif [ -e ${sysconfdir}/init.d/populate-volatile.sh ]; then
            ${sysconfdir}/init.d/populate-volatile.sh update
        fi
    fi
}

FILES_${PN} += "${libdir}/* ${datadir}/*"
