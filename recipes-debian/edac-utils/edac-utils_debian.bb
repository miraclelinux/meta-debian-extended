# base recipe: meta-openembedded-warrior/meta-oe/recipes-bsp/edac-utils/edac-utils_git.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "Userspace helper for Linux kernel EDAC drivers"
HOMEPAGE = "https://github.com/grondo/edac-utils"
SECTION = "Applications/System"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

DEPENDS = " sysfsutils"

SRCREV = "f9aa96205f610de39a79ff43c7478b7ef02e3138"
PV = "0.18+git${SRCPV}"

inherit debian-package
require recipes-debian/sources/edac-utils.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/grondo-edac-utils-ea4d2e7"

FILES_${PN} += "\
    ${systemd_system_unitdir} \
"

SRC_URI += " \
    file://edac.service \
	file://edac.init \
"

inherit autotools-brokensep

do_configure_prepend () {
    touch ${S}/ChangeLog
    ${S}/bootstrap
}

RDEPENDS_${PN}_x86 = "dmidecode"
RDEPENDS_${PN}_x86-64 = "dmidecode"
RDEPENDS_${PN}_arm = "dmidecode"
RDEPENDS_${PN}_aarch64 = "dmidecode"
RDEPENDS_${PN}_powerpc = "dmidecode"
RDEPENDS_${PN}_powerpc64 = "dmidecode"
RDEPENDS_${PN}_append = " \
    perl-module-file-basename perl-module-file-find perl-module-getopt-long perl-module-posix \
    perl-module-overload \
    perl-module-overloading \
    perl-module-file-glob \
"

do_install_append() {
	install -d ${D}${systemd_unitdir}/system
	install -m 644 ${WORKDIR}/edac.service ${D}/${systemd_unitdir}/system
	sed -i -e 's,@SBINDIR@,${sbindir},g' ${D}/${systemd_unitdir}/system/edac.service

    # override debian version of init sctipt
	install -m 755 ${WORKDIR}/edac.init ${D}/${sysconfdir}/init.d/edac
}

SYSTEMD_SERVICE_${PN} = "edac.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"
