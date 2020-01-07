# base recipe: meta-oe/recipes-extended/haveged/haveged_1.9.4.bb
# base branch: warrior
# base commit: eb6cf36865da9e3363479d93b5b5a1085bb8d2fa

SUMMARY = "haveged - A simple entropy daemon"
DESCRIPTION = "The haveged project is an attempt to provide an easy-to-use, unpredictable random number generator based upon an adaptation of the HAVEGE algorithm. Haveged was created to remedy low-entropy conditions in the Linux random device that can occur under some workloads, especially on headless servers."
AUTHOR = "Gary Wuertz"
HOMEPAGE = "http://www.issihosts.com/haveged/index.html"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM="file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit debian-package
require recipes-debian/sources/haveged.inc

SRC_URI += "file://0001-Remove-install-exec-hook-and-install-data-hook.patch"

inherit autotools update-rc.d systemd

EXTRA_OECONF = "\
    --enable-nistest=yes \
    --enable-olt=yes \
    --enable-threads=no \
"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}"
PACKAGECONFIG[systemd] = "--enable-init=service.redhat --enable-initdir=${systemd_system_unitdir}, --enable-init=sysv.redhat, systemd"
PACKAGECONFIG[sysvinit] = "--enable-init=sysv.redhat --enable-initdir=${sysconfdir}/init.d, --enable-init=sysv.redhat, sysvinit"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "haveged"
INITSCRIPT_PARAMS_${PN} = "defaults 9"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "haveged.service"

do_install_append() {
    # The exit status is 143 when the service is stopped
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -p -m644 ${S}/debian/haveged.service ${D}${systemd_system_unitdir}/haveged.service
    fi

    install -d ${D}${sysconfdir}/init.d/
    install -p -m755 ${B}/init.d/haveged ${D}${sysconfdir}/init.d/haveged
}

MIPS_INSTRUCTION_SET = "mips"
