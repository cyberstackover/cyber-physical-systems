package com.cyber.api.services;

import com.cyber.api.models.Device;
import com.cyber.api.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final String SALT = "matagaruda";

    private final Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Autowired
    private DeviceRepository deviceRepository;

    public void save(Device device) {
        String clientId = generateClientId(device.getClientName());
        String clientSecret = generateClientSecret(device.getClientName());

        device.setClientId(clientId);
        device.setClientSecret(clientSecret);

        deviceRepository.save(device);
    }

    public String generateClientId(String clientName) {
        String tail = clientName + System.currentTimeMillis();
        return encoder.encodePassword(clientName+tail, SALT);
    }

    public String generateClientSecret(String clientName) {
        String tail = clientName + System.currentTimeMillis();
        return encoder.encodePassword(tail, SALT);
    }

}
