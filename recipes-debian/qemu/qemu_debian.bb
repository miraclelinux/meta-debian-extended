# From poky/meta/recipes-devtools/qemu/qemu_3.1.0.bb
# Branch: warrior
# rev: 48522906a261f9a552f13b146aa7f3691be37002

BBCLASSEXTEND = "nativesdk"

require qemu.inc

DEPENDS = "glib-2.0 zlib pixman seabios"

RDEPENDS_${PN}_class-target += "bash seabios"

EXTRA_OECONF_append_class-target = " --target-list=${@get_qemu_target_list(d)}"
EXTRA_OECONF_append_class-nativesdk = " --target-list=${@get_qemu_target_list(d)}"

do_install_append_class-nativesdk() {
     ${@bb.utils.contains('PACKAGECONFIG', 'gtk+', 'make_qemu_wrapper', '', d)}
}

PACKAGECONFIG ??= " \
    fdt sdl kvm \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa xen', d)} \
"
PACKAGECONFIG_class-nativesdk ??= "fdt sdl kvm"

