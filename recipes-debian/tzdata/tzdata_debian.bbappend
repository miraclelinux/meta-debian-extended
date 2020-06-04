CONFFILES_tzdata-core = "${sysconfdir}/localtime"
CONFFILES_tzdata-core += "${@ "${sysconfdir}/timezone" if bb.utils.to_boolean(d.getVar('INSTALL_TIMEZONE_FILE')) else "" }"

