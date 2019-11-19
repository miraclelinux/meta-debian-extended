SUMMARY = "Helper tools for all init systems"
DESCRIPTION = "This package contains helper tools that are necessary for switching between \
    the various init systems that Debian contains (e.g. sysvinit, upstart, \
    systemd). An example is deb-systemd-helper, a script that enables systemd unit \
    files without depending on a running systemd. \
    While this package is maintained by pkg-systemd-maintainers, it is NOT \
    specific to systemd at all. Maintainers of other init systems are welcome to \
    include their helpers in this package."

HOMEPAGE = "https://packages.qa.debian.org/i/init-system-helpers.html"

PR = "r0"
inherit debian-package
require recipes-debian/sources/init-system-helpers.inc

LICENSE = "GPLv2+ & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://script/deb-systemd-invoke;beginline=3;endline=30;md5=42d36293e53d929566aef24e3e3b9ef8"

inherit allarch

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 script/* ${D}${sbindir}
    # Use update-rc.d provided by Poky's update-rc.d package
    rm ${D}${sbindir}/update-rc.d
}

RDEPENDS_${PN} = "perl"
