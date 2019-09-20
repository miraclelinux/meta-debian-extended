# base recipe: meta/recipes-extended/watchdog/watchdog_5.15.bb
# base branch: warrior
# base commit: 6d2e12e79211b31cdf5ea824fb9a8be54ba9a9eb

SUMMARY = "Software watchdog"
DESCRIPTION = "Watchdog is a daemon that checks if your system is still \
working. If programs in user space are not longer executed \
it will reboot the system."
HOMEPAGE = "http://watchdog.sourceforge.net/"
BUGTRACKER = "http://sourceforge.net/tracker/?group_id=172030&atid=860194"

LICENSE = "GPL-2.0+"
LIC_FILES_CHKSUM = "file://COPYING;md5=ecc0551bf54ad97f6b541720f84d6569"

require recipes-debian/sources/${BPN}.inc
inherit debian-package

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}"
DEBIAN_PATCH_TYPE = "nopatch"
FILESPATH_append = ":${COREBASE}/meta/recipes-extended/watchdog/watchdog"
SRC_URI += " \
           file://0001-Include-linux-param.h-for-EXEC_PAGESIZE-definition.patch \
           file://0001-watchdog-remove-interdependencies-of-watchdog-and-wd.patch \
           file://watchdog.init \
           file://wd_keepalive.init \
"

inherit autotools update-rc.d systemd pkgconfig

DEPENDS += "libtirpc"
CFLAGS += "-I${STAGING_INCDIR}/tirpc"
LDFLAGS += "-ltirpc"

EXTRA_OECONF += " --disable-nfs "

INITSCRIPT_PACKAGES = "${PN} ${PN}-keepalive"

INITSCRIPT_NAME_${PN} = "watchdog"
INITSCRIPT_PARAMS_${PN} = "start 25 1 2 3 4 5 . stop 85 0 6 ."

INITSCRIPT_NAME_${PN}-keepalive = "wd_keepalive"
INITSCRIPT_PARAMS_${PN}-keepalive = "start 25 1 2 3 4 5 . stop 85 0 6 ."

SYSTEMD_PACKAGES = "${PN} ${PN}-keepalive"
SYSTEMD_SERVICE_${PN} = "watchdog.service"
SYSTEMD_SERVICE_${PN}-keepalive = "wd_keepalive.service"
# When using systemd, consider making use of internal watchdog support of systemd.
# See RuntimeWatchdogSec in /etc/systemd/system.conf.
SYSTEMD_AUTO_ENABLE = "disable"

do_install_append() {
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${S}/debian/watchdog.service ${D}${systemd_system_unitdir}
	install -m 0644 ${S}/debian/wd_keepalive.service ${D}${systemd_system_unitdir}

	install -Dm 0755 ${WORKDIR}/watchdog.init ${D}/${sysconfdir}/init.d/watchdog
	install -Dm 0755 ${WORKDIR}/wd_keepalive.init ${D}${sysconfdir}/init.d/wd_keepalive

	# watchdog.conf is provided by the watchdog-config recipe
	rm ${D}${sysconfdir}/watchdog.conf
}

do_debian_unpack_extra() {
	cd ${DEBIAN_UNPACK_DIR}
	patch -p1 < ${WORKDIR}/${BPN}_${DPV}.diff
}

PACKAGES =+ "${PN}-keepalive"

FILES_${PN}-keepalive = " \
    ${sysconfdir}/init.d/wd_keepalive \
    ${systemd_system_unitdir}/wd_keepalive.service \
    ${sbindir}/wd_keepalive \
"

RDEPENDS_${PN} += "${PN}-config"
RRECOMMENDS_${PN} += "kernel-module-softdog"

RDEPENDS_${PN}-keepalive += "${PN}-config"
RCONFLICTS_${PN}-keepalive += "${PN}"
RRECOMMENDS_${PN}-keepalive += "kernel-module-softdog"
